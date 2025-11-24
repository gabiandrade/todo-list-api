package com.andrade.todo_list_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {

    @NotNull
    private String title;
    private String description;
    // ISO-8601 string, e.g. 2025-12-01T15:30
    private String dueDate;

    @NotNull
    private String status;

    @JsonProperty("isPublic")
    private boolean isPublic;
    @NotNull
    private Long ownerId;

    private List<Long> participantIds;
}
