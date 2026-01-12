package com.andrade.todo_list_api.domain.dto.external;

import lombok.Data;

@Data
public class TodoItem {

    private Long id;
    private String todo;
    private boolean completed;
    private Long userId;
}
