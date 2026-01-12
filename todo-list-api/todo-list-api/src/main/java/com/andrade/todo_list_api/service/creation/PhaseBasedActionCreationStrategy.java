package com.andrade.todo_list_api.service.creation;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.Phase;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;
import com.andrade.todo_list_api.domain.dto.request.PhaseCreateRequest;
import com.andrade.todo_list_api.service.ActionAggregationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class PhaseBasedActionCreationStrategy implements ActionCreationStrategy {

    private final ActionAggregationService aggregationService;

    @Override
    public boolean supports(ActionCreateRequest request) {
        return request.getPhases() != null && !request.getPhases().isEmpty();
    }

    @Override
    public Action create(ActionCreateRequest request) {

        Action action = new Action();
        action.setName(request.getName());

        var phases = request.getPhases().stream()
                .map(phaseCreateRequest -> toPhase(phaseCreateRequest, action))
                .toList();

        action.setPhases(phases);

        aggregationService.aggregate(action);

        return action;
    }

    private Phase toPhase(PhaseCreateRequest request, Action action) {

        Phase phase = new Phase();
        phase.setPhaseName(request.getName());
        phase.setResponsible(request.getResponsible());
        phase.setStartDate(request.getStartDate());
        phase.setPlannedEndDate(request.getPlannedEndDate());
        phase.setEffectiveEndDate(request.getEffectiveEndDate());
        phase.setCost(request.getCost());
        phase.setParticipants(request.getParticipants());
        phase.setAction(action);

        return phase;
    }

}
