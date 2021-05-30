package dev.rafaelcmr.controller;

import dev.rafaelcmr.models.Todo;
import dev.rafaelcmr.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {

    @Autowired
    public TodoService service;

    @GetMapping("/todos")
    public ResponseEntity<Page<Todo>> getTodos() {
        return ResponseEntity.ok(service.paginate());
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(this.service.find(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Errou", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Todo> postTodo(@RequestBody Todo model) {
        try {
            return new ResponseEntity<Todo>(this.service.save(model), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/todos/{id}")
    public String putTodos(@RequestBody Todo model, @PathVariable Long id) {
        this.service.update(id, model);
        return  id + " Atualizado com sucesso!!";
    }

    @DeleteMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodos(@PathVariable Long id) {

        try {
            this.service.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
