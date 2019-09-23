package pl.solutions.software.sokolik.bartosz.book;

import static org.junit.Assert.*;

import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.solutions.software.sokolik.bartosz.book.domain.dto.BookNotFoundResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  public void shouldReturnBookNotFoundResponse() {
    BookNotFoundResponse expected = new BookNotFoundResponse();

    ResponseEntity<BookNotFoundResponse> response = restTemplate
        .getForEntity("http://localhost:" + port + "/api/books/" + UUID.randomUUID(),
            BookNotFoundResponse.class);

    BookNotFoundResponse actual = response.getBody();

    assertEquals(expected, actual);
  }
}
