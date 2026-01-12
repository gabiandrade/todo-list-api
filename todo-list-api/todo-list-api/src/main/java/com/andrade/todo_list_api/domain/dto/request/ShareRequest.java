package com.andrade.todo_list_api.domain.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ShareRequest {
    private List<Long> participantIds;
}
