package com.andrade.todo_list_api.domain.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
    private boolean isPublic;
    private Long ownerId;
    private List<Long> participantIds;
}
