package dev.rafaelcmr.repository;

import dev.rafaelcmr.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
