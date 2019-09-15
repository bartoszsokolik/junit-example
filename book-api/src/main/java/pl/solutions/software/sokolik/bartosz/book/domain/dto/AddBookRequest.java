package pl.solutions.software.sokolik.bartosz.book.domain.dto;

import java.util.Objects;

public class AddBookRequest {

  private String title;

  private String isbn;

  public AddBookRequest() {
  }

  public AddBookRequest(String title, String isbn) {
    this.title = title;
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getIsbn() {
    return isbn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddBookRequest that = (AddBookRequest) o;
    return Objects.equals(title, that.title) &&
        Objects.equals(isbn, that.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, isbn);
  }
}
