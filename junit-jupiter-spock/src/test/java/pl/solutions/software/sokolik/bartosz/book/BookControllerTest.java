package pl.solutions.software.sokolik.bartosz.book;

import static com.jayway.jsonpath.JsonPath.read;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.solutions.software.sokolik.bartosz.book.domain.BookFacade;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest extends AbstractBookControllerTest {

    @MockBean
    private BookFacade bookFacade;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should add new book")
    void shouldCreateBook() throws Exception {
        //given
        AddBookRequest request = new AddBookRequest();

        when(bookFacade.addBook(any(AddBookRequest.class))).thenReturn(new AddBookResponse(ID));

        //when
        ResultActions result = mockMvc.perform(post("/api/books")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isCreated());

        String body = result.andReturn()
            .getResponse()
            .getContentAsString();

        assertAll(
            () -> assertEquals(BOOK_CREATED_MESSAGE, read(body, "$.message")),
            () -> assertEquals(ID.toString(), read(body, "$.id"))
        );

        verify(bookFacade).addBook(request);
    }

    @Test
    @DisplayName("should return book with given id")
    void shouldReturnBookWithId() throws Exception {
        //given
        BookDto response = new BookDto(ID, TITLE, ISBN);

        when(bookFacade.findById(any(UUID.class))).thenReturn(response);

        //when
        ResultActions result = mockMvc.perform(get("/api/books/" + ID));

        //then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(response.getMessage()))
            .andExpect(jsonPath("$.id").value(response.getId().toString()))
            .andExpect(jsonPath("$.title").value(response.getTitle()))
            .andExpect(jsonPath("$.isbn").value(response.getIsbn()));

        verify(bookFacade).findById(ID);
    }

    @Test
    @DisplayName("should return book not found response")
    void shouldReturnBookNotFound() throws Exception {
        //given
        BookNotFoundResponse response = new BookNotFoundResponse();

        when(bookFacade.findById(any(UUID.class))).thenReturn(response);

        //when
        ResultActions result = mockMvc.perform(get("/api/books/" + ID));

        //then
        result.andExpect(jsonPath("$.message").value(response.getMessage()));
    }
}
