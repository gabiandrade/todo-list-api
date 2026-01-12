package com.andrade.todo_list_api.dto.external;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeListResponse {

    private List<EmployeeResponse> users;
    private Integer total;
    private Integer skip;
    private Integer limit;

}
