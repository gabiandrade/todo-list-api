package com.andrade.todo_list_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phaseName;
    @ManyToOne
    @JoinColumn(name = "responsible")
    private Employee responsible;

    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate effectiveDate;

    private BigDecimal cost = BigDecimal.ZERO;

    @ManyToMany
    @JoinTable(
            name = "phase_participants",
            joinColumns = @JoinColumn(name = "phase_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> participants = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_id")
    private Action action;
}
