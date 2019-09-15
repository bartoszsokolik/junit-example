package pl.solutions.software.sokolik.bartosz.book.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InMemoryRepository<T> {

  UUID save(T entity);

  Optional<T> findById(UUID id);

  List<T> findAll();
}
