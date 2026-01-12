package com.andrade.todo_list_api.controller;


import com.andrade.todo_list_api.domain.Action;
import com.andrade.todo_list_api.domain.dto.request.ActionCreateRequest;
import com.andrade.todo_list_api.service.ActionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService service;

    public ActionController(ActionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Action create(@RequestBody ActionCreateRequest request) {
        return service.create(request);
    }
}
