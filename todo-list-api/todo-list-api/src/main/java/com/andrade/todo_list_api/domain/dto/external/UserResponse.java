package com.andrade.todo_list_api.domain.dto.external;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
