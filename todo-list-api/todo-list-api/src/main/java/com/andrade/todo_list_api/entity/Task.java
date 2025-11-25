package com.andrade.todo_list_api.entity;

import com.andrade.todo_list_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
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
