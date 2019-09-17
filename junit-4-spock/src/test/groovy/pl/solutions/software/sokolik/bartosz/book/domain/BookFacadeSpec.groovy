package pl.solutions.software.sokolik.bartosz.book.domain


import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse
import spock.lang.Specification


class BookFacadeSpec extends Specification {

    private static final UUID ID = UUID.fromString("7b1ccba0-3fdc-4fef-a392-3f5f3eceb979")

    private InMemoryRepository<Book> bookRepository

    private BookFacade sut

    def setup() {
        bookRepository = Mock()
        sut = new BookFacade(bookRepository)
    }

    def "should add book to repository"() {
        given:
        AddBookRequest request = Stub()
        AddBookResponse expected = new AddBookResponse(ID)

        bookRepository.save(_ as Book) >> ID

        when:
        def actual = (AddBookResponse) sut.addBook(request)

        then:
        actual == expected
    }
}
