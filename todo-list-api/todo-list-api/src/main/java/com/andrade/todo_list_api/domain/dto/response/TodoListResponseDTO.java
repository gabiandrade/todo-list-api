package com.andrade.todo_list_api.domain.dto.response;

import com.andrade.todo_list_api.domain.dto.TodoItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class TodoListResponseDTO {
    private List<TodoItemDTO> todos;
    private int total;
    private int skip;
    private int limit;
}
