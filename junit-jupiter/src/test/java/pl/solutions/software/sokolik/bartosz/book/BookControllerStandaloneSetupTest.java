package pl.solutions.software.sokolik.bartosz.book;

import java.util.UUID;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.solutions.software.sokolik.bartosz.book.domain.BookFacade;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse;

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
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class BookControllerStandaloneSetupTest extends AbstractBookControllerTest {

    @Mock
    private BookFacade bookFacade;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateBook() throws Exception {
        //given
        AddBookRequest request = new AddBookRequest();

        when(bookFacade.addBook(any(AddBookRequest.class))).thenReturn(new AddBookResponse(ID));

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/books")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value(BOOK_CREATED_MESSAGE))
            .andExpect(jsonPath("$.id").value(ID.toString()));

        String body = resultActions.andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals(BOOK_CREATED_MESSAGE, read(body, "$.message"));
        assertEquals(ID.toString(), read(body, "$.id"));

        verify(bookFacade).addBook(request);
    }

    @Test
    void shouldReturnBookWithGivenId() throws Exception {
        //given
        BookDto bookResponse = new BookDto(ID, TITLE, ISBN);

        when(bookFacade.findById(ID)).thenReturn(bookResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/books/" + ID));

        //then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(BOOK_FOUND_MESSAGE))
            .andExpect(jsonPath("$.id").value(bookResponse.getId().toString()))
            .andExpect(jsonPath("$.title").value(bookResponse.getTitle()))
            .andExpect(jsonPath("$.isbn").value(bookResponse.getIsbn()));

        String body = resultActions.andReturn()
            .getResponse()
            .getContentAsString();

        assertAll(
            () -> assertEquals(BOOK_FOUND_MESSAGE, read(body, "$.message")),
            () -> assertEquals(bookResponse.getId().toString(), read(body, "$.id")),
            () -> assertEquals(bookResponse.getTitle(), read(body, "$.title")),
            () -> assertEquals(bookResponse.getIsbn(), read(body, "$.isbn"))
        );
    }

    @Test
    void shouldReturnResponseWithNotFoundMessage() throws Exception {
        //given
        BookNotFoundResponse response = new BookNotFoundResponse();

        when(bookFacade.findById(any(UUID.class))).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/books/" + ID));

        //then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(BOOK_NOT_FOUND_MESSAGE));

        verify(bookFacade).findById(ID);
    }
}
