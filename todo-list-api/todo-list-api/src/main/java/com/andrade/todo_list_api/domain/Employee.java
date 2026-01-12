package com.andrade.todo_list_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Employee {

    @Id
    private Long id;
    private String name;
    private String email;
}
