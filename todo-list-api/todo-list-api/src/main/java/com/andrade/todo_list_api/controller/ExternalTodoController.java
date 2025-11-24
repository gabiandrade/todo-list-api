package com.andrade.todo_list_api.controller;

import com.andrade.todo_list_api.dto.TodoItemDTO;
import com.andrade.todo_list_api.dto.TodoListResponseDTO;
import com.andrade.todo_list_api.service.ExternalTodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/todos")
@RequiredArgsConstructor
public class ExternalTodoController {

    private final ExternalTodoService externalTodoService;


    @GetMapping
    public TodoListResponseDTO all() {
        return externalTodoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoItemDTO getById(@PathVariable Long id) {
        return externalTodoService.getTodoById(id);
    }
}
