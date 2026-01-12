package com.andrade.todo_list_api.service.creation;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class SimpleActionCreationStrategy implements ActionCreationStrategy {
    @Override
    public boolean supports(ActionCreateRequest request) {
        return request.getPhases() == null || request.getPhases().isEmpty();
    }

    @Override
    public Action create(ActionCreateRequest request) {
        Action action = new Action();
        action.setName(request.getName());
        action.setResponsible(request.getResponsible());
        action.setStartDate(request.getStartDate());
        action.setPlannedEndDate(request.getPlannedEndDate());
        action.setEffectiveEndDate(request.getEffectiveEndDate());
        action.setTotalCost(request.getTotalCost());
        action.setParticipants(request.getParticipants());

        return action;
    }
}

