package pl.solutions.software.sokolik.bartosz.book.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
class InMemoryBookRepository implements InMemoryRepository<Book> {

  private final Map<UUID, Book> repository = new ConcurrentHashMap<>();

  @Override
  public UUID save(Book entity) {
    repository.put(entity.getId(), entity);
    return entity.getId();
  }

  @Override
  public Optional<Book> findById(UUID id) {
    return Optional.ofNullable(repository.get(id));
  }

  @Override
  public List<Book> findAll() {
    return new ArrayList<>(repository.values());
  }
}
