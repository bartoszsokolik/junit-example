package pl.solutions.software.sokolik.bartosz.book.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookRequest;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.AddBookResponse;

@RunWith(MockitoJUnitRunner.class)
public class BookFacadeTest {

  private static final UUID ID = UUID.fromString("7b1ccba0-3fdc-4fef-a392-3f5f3eceb979");
  private static final String TITLE = "Harry Potter";
  private static final String ISBN = "1234";

  @Mock
  private InMemoryRepository<Book> bookRepository;

  @InjectMocks
  private BookFacade sut;

  @Test
  public void shouldAddBookToRepository() {
    //given
    AddBookRequest request = new AddBookRequest(TITLE, ISBN);

    AddBookResponse expected = new AddBookResponse(ID);

    when(bookRepository.save(any(Book.class))).thenReturn(ID);

    //when
    AddBookResponse actual = (AddBookResponse) sut.addBook(request);

    //then
    assertEquals(expected, actual);
  }

}
