package com.andrade.todo_list_api.service;

import com.andrade.todo_list_api.domain.dto.TodoItemDTO;
import com.andrade.todo_list_api.domain.dto.response.TodoListResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalTodoService {

    private final WebClient webClient;

    public ExternalTodoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public TodoListResponseDTO getAllTodos() {
        return webClient.get()
                .uri("/todos")
                .retrieve()
                .bodyToMono(TodoListResponseDTO.class)
                .block();
    }

    public TodoItemDTO getTodoById(Long id) {
        return webClient.get()
                .uri("/todos/{id}", id)
                .retrieve()
                .bodyToMono(TodoItemDTO.class)
                .block();
    }
}
