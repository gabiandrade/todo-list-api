package com.andrade.todo_list_api.service;

import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.Phase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class ActionAggregationService {

    public void aggregate(Action action) {

        action.setTotalCost(
                action.getPhases().stream()
                        .map(Phase::getCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        action.setStartDate(
                action.getPhases().stream()
                        .map(Phase::getStartDate)
                        .min(LocalDate::compareTo)
                        .orElse(null)
        );

        action.setPlannedEndDate(
                action.getPhases().stream()
                        .map(Phase::getPlannedEndDate)
                        .max(LocalDate::compareTo)
                        .orElse(null)
        );

        action.setEffectiveEndDate(
                action.getPhases().stream()
                        .map(Phase::getEffectiveEndDate)
                        .filter(Objects::nonNull)
                        .max(LocalDate::compareTo)
                        .orElse(null)
        );
    }
}
