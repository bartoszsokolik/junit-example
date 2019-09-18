package pl.solutions.software.sokolik.bartosz.book

import groovy.json.JsonBuilder
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import pl.solutions.software.sokolik.bartosz.book.domain.BookFacade
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse

import static com.jayway.jsonpath.JsonPath.read
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController.class)
class BookControllerSpec extends AbstractBookControllerSpecification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookFacade bookFacade = Mock()

    def "should create book"() {
        given:
        AddBookRequest request = new AddBookRequest()
        AddBookResponse expected = new AddBookResponse(id)

        and:
        bookFacade.addBook(_ as AddBookRequest) >> expected

        when:
        def result = mockMvc.perform(post("/api/books")
            .contentType(APPLICATION_JSON)
            .content(new JsonBuilder(request).toPrettyString()))

        then:
        result.andExpect(status().isCreated())

        and:
        def body = result.andReturn()
            .getResponse()
            .getContentAsString()

        expected.getId().toString() == read(body, '$.id')
        expected.getMessage() == read(body, '$.message')

        and:
        1 * bookFacade.addBook(_ as AddBookRequest) >> expected
    }

    def "should return book with given id"() {
        given:
        BookDto expected = new BookDto(id, title, isbn)

        bookFacade.findById(_ as UUID) >> expected

        when:
        def result = mockMvc.perform(get("/api/books/" + id))

        then:
        result.andExpect(status().isOk())

        and:
        def body = result.andReturn()
            .getResponse()
            .getContentAsString()

        expected.getMessage() == read(body, '$.message')
        expected.getId().toString() == read(body, '$.id')
        expected.getTitle() == read(body, '$.title')
        expected.getIsbn() == read(body, '$.isbn')
    }

    def "should return response with no book found message"() {
        given:
        BookNotFoundResponse response = new BookNotFoundResponse()

        bookFacade.findById(_ as UUID) >> response

        when:
        def result = mockMvc.perform(get("/api/books/" + id))

        then:
        def body = result.andReturn()
            .getResponse()
            .getContentAsString()

        response.getMessage() == read(body, '$.message')

        1 * bookFacade.findById(id) >> response
    }
}
