package com.msk.ToDoList.controller;

import com.msk.ToDoList.model.ToDo;
import com.msk.ToDoList.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final TodoService todoService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createTodo(@PathVariable String userId, @RequestBody ToDo toDo) {
        try {
            todoService.createTodo(userId, toDo);
            return new ResponseEntity<>("todo created", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
