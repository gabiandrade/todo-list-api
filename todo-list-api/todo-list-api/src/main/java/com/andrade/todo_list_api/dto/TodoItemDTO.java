package com.andrade.todo_list_api.dto;

import lombok.Data;

@Data
public class TodoItemDTO {

    private Long id;
    private String todo;
    private boolean completed;
    private Long userId;
}
