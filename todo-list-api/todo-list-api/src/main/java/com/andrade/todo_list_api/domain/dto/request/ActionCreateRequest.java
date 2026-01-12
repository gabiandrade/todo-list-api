package com.andrade.todo_list_api.domain.dto.request;

import com.andrade.todo_list_api.domain.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class ActionCreateRequest {

    private Long id;
    private String name;
    private Employee responsible;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate effectiveEndDate;
    private BigDecimal totalCost;
    private Set<Employee> participants;
    private List<PhaseCreateRequest> phases;
}
