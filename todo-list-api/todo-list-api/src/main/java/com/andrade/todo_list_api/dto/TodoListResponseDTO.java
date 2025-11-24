package com.andrade.todo_list_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class TodoListResponseDTO {
    private List<TodoItemDTO> todos;
    private int total;
    private int skip;
    private int limit;
}
