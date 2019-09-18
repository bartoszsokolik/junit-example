package pl.solutions.software.sokolik.bartosz.book.domain;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookFacadeTest {

    private static final UUID ID = UUID.fromString("7b1ccba0-3fdc-4fef-a392-3f5f3eceb979");
    private static final String TITLE = "Harry Potter";
    private static final String ISBN = "1234";

    @Mock
    private InMemoryRepository<Book> bookRepository;

    @InjectMocks
    private BookFacade sut;

    @Test
    @DisplayName("should add book to repository")
    void shouldAddBookToRepository() {
        //given
        AddBookRequest request = new AddBookRequest();
        AddBookResponse expected = new AddBookResponse(ID);

        when(bookRepository.save(any(Book.class))).thenReturn(ID);

        //when
        AddBookResponse actual = (AddBookResponse) sut.addBook(request);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return response with existing book id")
    void shouldGetBookWithId() {
        //given
        Book book = Book.builder()
            .id(ID)
            .title(TITLE)
            .isbn(ISBN)
            .build();

        BookDto expected = new BookDto(ID, TITLE, ISBN);

        when(bookRepository.findById(any(UUID.class))).thenReturn(Optional.of(book));

        //when
        BookDto actual = (BookDto) sut.findById(ID);

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return book not found response")
    void bookNotFound() {
        //given
        BookNotFoundResponse expected = new BookNotFoundResponse();

        when(bookRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        //when
        BookNotFoundResponse actual = (BookNotFoundResponse) sut.findById(ID);

        //then
        assertEquals(expected, actual);
    }
}
