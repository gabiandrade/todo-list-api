package com.andrade.todo_list_api.dto.external;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String email;
}
