package pl.solutions.software.sokolik.bartosz.book.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookDto;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookResponse;

@Service
public class BookFacade {

  private final InMemoryRepository<Book> bookRepository;

  public BookFacade(InMemoryRepository<Book> bookRepository) {
    this.bookRepository = bookRepository;
  }

  public BookResponse addBook(AddBookRequest request) {
    Book book = Book.builder()
        .id(UUID.randomUUID())
        .title(request.getTitle())
        .isbn(request.getIsbn())
        .build();

    return new AddBookResponse(bookRepository.save(book));
  }

  public BookResponse findById(UUID id) {
    return bookRepository.findById(id)
        .map(this::mapBookToResponse)
        .orElse(new BookNotFoundResponse());
  }

  public List<BookResponse> findAllBooks() {
    return bookRepository.findAll()
        .stream()
        .map(this::mapBookToResponse)
        .collect(toList());
  }

  private BookResponse mapBookToResponse(Book book) {
    return new BookDto(book.getId(), book.getTitle(), book.getIsbn());
  }

}
