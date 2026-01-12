package com.andrade.todo_list_api.controller;

import com.andrade.todo_list_api.domain.dto.request.ShareRequest;
import com.andrade.todo_list_api.domain.dto.TaskDTO;
import com.andrade.todo_list_api.domain.dto.response.TaskResponse;
import com.andrade.todo_list_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    // list tasks visible to a user
    @GetMapping
    public List<TaskResponse> list(@RequestParam Long userId) {
        return taskService.listVisible(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Validated @RequestBody TaskDTO dto) {
        return taskService.create(dto);
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id, @RequestParam Long userId) {
        return taskService.getById(id, userId);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestParam Long userId, @Validated @RequestBody TaskDTO dto) {
        return taskService.update(id, userId, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestParam Long userId) {
        taskService.delete(id, userId);
    }

    @PostMapping("/{id}/share")
    public TaskResponse share(@PathVariable Long id, @RequestParam Long userId, @RequestBody ShareRequest req) {
        return taskService.share(id, userId, req.getParticipantIds());
    }
}
