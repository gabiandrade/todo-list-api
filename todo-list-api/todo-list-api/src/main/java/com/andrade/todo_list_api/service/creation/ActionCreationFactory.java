package com.andrade.todo_list_api.service.creation;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActionCreationFactory {

    private final List<ActionCreationStrategy> strategies;

    public ActionCreationFactory(List<ActionCreationStrategy> strategies) {
        this.strategies = strategies;
    }

    public Action create(ActionCreateRequest request) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(request))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No ActionCreationStrategy found for request"
                ))
                .create(request);
    }
}
