package com.andrade.todo_list_api.domain.dto.request;

import com.andrade.todo_list_api.domain.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class PhaseCreateRequest {

    private String name;
    private Employee responsible;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate effectiveEndDate;
    private BigDecimal cost;
    private Set<Employee> participants;

}
