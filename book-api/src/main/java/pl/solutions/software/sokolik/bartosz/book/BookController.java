package pl.solutions.software.sokolik.bartosz.book;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.solutions.software.sokolik.bartosz.book.domain.BookFacade;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookResponse;

@RestController
@RequestMapping("/api/books")
class BookController {

  private final BookFacade bookFacade;

  public BookController(BookFacade bookFacade) {
    this.bookFacade = bookFacade;
  }

  @PostMapping
  ResponseEntity<BookResponse> addBook(@RequestBody AddBookRequest request) {
    return new ResponseEntity<>(bookFacade.addBook(request), CREATED);
  }

  @GetMapping("/{uuid}")
  ResponseEntity<BookResponse> findBookById(@PathVariable UUID uuid) {
    return new ResponseEntity<>(bookFacade.findById(uuid), OK);
  }

  @GetMapping
  List<BookResponse> findAllBooks() {
    return bookFacade.findAllBooks();
  }
}
