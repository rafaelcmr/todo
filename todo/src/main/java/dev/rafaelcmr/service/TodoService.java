package dev.rafaelcmr.service;

import dev.rafaelcmr.models.Todo;
import dev.rafaelcmr.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo find(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    public Page<Todo> paginate() {
        return this.repository.findAll(PageRequest.of(0, 5));
    }

    public Todo save(Todo model) {
        this.repository.save(model);
        return model;
    }

    public void update(Long id, Todo model) {
        Todo query = repository.findById(id).orElseThrow();
        model.setId(id);
        this.repository.save(model);
    }

    public void delete(Long id) {
        Todo query = this.repository.findById(id).orElseThrow();
        this.repository.deleteById(id);
    }

    public int count() {
        return (int) this.repository.count();
    }
}
