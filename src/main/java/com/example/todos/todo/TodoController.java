package com.example.todos.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@PathVariable long id) {
        return todoRepository.findById(id);
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todoRepository.save(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo editTodo(@PathVariable long id, @RequestBody Todo todo) {
        Todo existingTodo = new Todo();
        try {
            existingTodo = todoRepository.findById(id).get();
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setComplete(todo.isComplete());
            todoRepository.save(existingTodo);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            existingTodo = todoRepository.save(todo);
        }
        return existingTodo;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable long id) {
        todoRepository.deleteById(id);
    }
}