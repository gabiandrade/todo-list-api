package com.andrade.todo_list_api.service.creation;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;

public interface ActionCreationStrategy {

    boolean supports(ActionCreateRequest request);

    Action create(ActionCreateRequest request);
}
