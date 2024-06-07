package com.example.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping("/list/{user_id}")
    public ResponseEntity<List<TodoList>> list(@PathVariable Long user_id) {
        List<TodoList> findTodoList = todoRepository.findByUserId(user_id);


        return ResponseEntity.ok(findTodoList);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TodoList todo) {
        todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{item_id}")
    public ResponseEntity<Void> update(@PathVariable long item_id, @RequestBody TodoList todoList) {
       todoRepository.findById(item_id).ifPresentOrElse(
                todo -> {
                    todo.setTitle(todoList.getTitle());
                    todo.setContent(todoList.getContent());
                    todoRepository.save(todo);
                },
                () -> {
                    throw new RuntimeException("존재하지 않는 Todo입니다.");
                }
        );
       return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/todo/{item_id}")
    public ResponseEntity<Void> delete(@PathVariable long item_id) {
        todoRepository.deleteById(item_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
