package com.andrade.todo_list_api.repository;

import com.andrade.todo_list_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
            // select * from task where ownerId = 1;
    List<Task> findByOwnerId(Long ownerId);

    List<Task> findByParticipantsId(Long participantsId);

    List<Task> findByOwnerIdOrParticipantsId(Long ownerId, Long participantId);

    List<Task> findByDueDate(LocalDateTime dueDate);
}
