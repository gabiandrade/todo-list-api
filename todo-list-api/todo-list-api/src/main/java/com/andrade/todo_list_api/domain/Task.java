package com.andrade.todo_list_api.domain;

import com.andrade.todo_list_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 2000)
    private String description;
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isPublic;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(name = "task_participants",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants = new ArrayList<>();
}
