package pl.solutions.software.sokolik.bartosz.book


import groovy.json.JsonOutput
import org.springframework.test.web.servlet.MockMvc
import pl.solutions.software.sokolik.bartosz.book.domain.BookFacade
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto

import static com.jayway.jsonpath.JsonPath.read
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class BookControllerStandaloneSetupSpec extends AbstractBookControllerSpec {

    private BookFacade bookFacade

    private BookController bookController

    private MockMvc mockMvc

    def setup() {
        bookFacade = Mock()
        bookController = new BookController(bookFacade)

        mockMvc = standaloneSetup(bookController).build()
    }

    def "should create book"() {
        given:
        AddBookRequest request = new AddBookRequest()
        AddBookResponse expected = new AddBookResponse(ID)

        and:
        bookFacade.addBook(_ as AddBookRequest) >> expected

        when:
        def result = mockMvc.perform(post("/api/books")
                .contentType(APPLICATION_JSON)
                .content(JsonOutput.toJson(request)))

        then:
        result.andExpect(status().isCreated())
                .andExpect(jsonPath('$.message').value(BOOK_CREATED_MESSAGE))
                .andExpect(jsonPath('$.id').value(ID.toString()))

        and:
        1 * bookFacade.addBook(request) >> expected
    }

    def "should return book with given id"() {
        given:
        BookDto expected = new BookDto(ID, TITLE, ISBN)

        bookFacade.findById(_ as UUID) >> expected

        when:
        def result = mockMvc.perform(get("/api/books/" + ID))

        then:
        result.andExpect(status().isOk())

        def body = result.andReturn()
                .getResponse()
                .getContentAsString()

        expected.getMessage() == read(body, '$.message')
        expected.getId().toString() == read(body, '$.id')
        expected.getTitle() == read(body, '$.title')
        expected.getIsbn() == read(body, '$.isbn')

        and:
        1 * bookFacade.findById(ID) >> expected
    }

    def "should return response with no book found message"() {

    }

}
