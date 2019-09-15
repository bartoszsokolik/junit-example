package pl.solutions.software.sokolik.bartosz.book.domain;

import java.util.Objects;
import java.util.UUID;

class Book {

  private UUID id;

  private String title;

  private String isbn;

  private Book(UUID id, String title, String isbn) {
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

  public static BookBuilder builder() {
    return new BookBuilder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(id, book.id) &&
        Objects.equals(title, book.title) &&
        Objects.equals(isbn, book.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, isbn);
  }

  public static final class BookBuilder {
    private UUID id;
    private String title;
    private String isbn;

    private BookBuilder() {

    }

    public BookBuilder id(UUID id) {
      this.id = id;
      return this;
    }

    public BookBuilder title(String title) {
      this.title = title;
      return this;
    }

    public BookBuilder isbn(String isbn) {
      this.isbn = isbn;
      return this;
    }

    public Book build() {
      return new Book(id, title, isbn);
    }
  }
}
