package pl.solutions.software.sokolik.bartosz.book.domain.dto;

import java.util.Objects;
import java.util.UUID;

public class AddBookResponse extends BookResponse {

  private UUID id;

  public AddBookResponse() {
  }

  public AddBookResponse(UUID id) {
    super("Book created");
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    AddBookResponse response = (AddBookResponse) o;
    return Objects.equals(id, response.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
