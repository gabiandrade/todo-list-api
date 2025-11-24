package com.andrade.todo_list_api.service;

import com.andrade.todo_list_api.dto.external.TodoListResponse;
import com.andrade.todo_list_api.dto.external.UserListResponse;
import com.andrade.todo_list_api.entity.Task;
import com.andrade.todo_list_api.entity.User;
import com.andrade.todo_list_api.enums.Status;
import com.andrade.todo_list_api.repository.TaskRepository;
import com.andrade.todo_list_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final WebClient webClient;
    private final UserRepository userRepo;
    private final TaskRepository taskRepo;


    @PostConstruct
    public void seed() {
        try {
            // fetch users
            UserListResponse users = webClient.get()
                    .uri("/users")
                    .retrieve()
                    .bodyToMono(UserListResponse.class)
                    .block();

            if (users != null && users.getUsers() != null) {
                List<User> list = users.getUsers().stream().map(u -> {
                    User user = new User();
                    user.setId(u.getId());
                    user.setFirstName(u.getFirstName());
                    user.setLastName(u.getLastName());
                    user.setEmail(u.getEmail());
                    return user;
                }).collect(Collectors.toList());
                userRepo.saveAll(list);
            }

            // fetch todos and convert to tasks (basic mapping)
            TodoListResponse todos = webClient.get()
                    .uri("/todos")
                    .retrieve()
                    .bodyToMono(TodoListResponse.class)
                    .block();

            if (todos != null && todos.getTodos() != null) {
                List<Task> tasks = todos.getTodos().stream().map(t -> {
                    Task task = new Task();
                    task.setTitle(t.getTodo());
                    task.setDescription(null);
                    task.setStatus(t.isCompleted() ? Status.DONE : Status.TODO);
                    task.setPublic(false);
                    // try to set owner if user exists
                    userRepo.findById(t.getUserId()).ifPresent(task::setOwner);
                    return task;
                }).collect(Collectors.toList());
                taskRepo.saveAll(tasks);
            }
        } catch (Exception ex) {
            System.out.println("Seed error: " + ex.getMessage());
        }
    }
}
