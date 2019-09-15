package pl.solutions.software.sokolik.bartosz.book.domain.dto;

import java.util.UUID;

public class BookDto extends BookResponse {

  private UUID id;

  private String title;

  private String isbn;

  public BookDto() {
  }

  public BookDto(UUID id, String title, String isbn) {
    super("Success");
    this.id = id;
    this.title = title;
    this.isbn = isbn;
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getIsbn() {
    return isbn;
  }
}
