package pl.solutions.software.sokolik.bartosz.book.domain.dto;

import java.util.Objects;

public class BookResponse {

  protected String message;

  public BookResponse() {
  }

  public BookResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookResponse that = (BookResponse) o;
    return Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }
}
