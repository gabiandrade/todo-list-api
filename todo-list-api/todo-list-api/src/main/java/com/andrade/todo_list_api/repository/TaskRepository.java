package com.andrade.todo_list_api.repository;

import com.andrade.todo_list_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOwnerId(Long ownerId);
    List<Task> findByParticipantsId(Long participantsId);
    List<Task> findByOwnerIdOrParticipantsId(Long ownerId, Long participantId);
    List<Task> findByDueDate(LocalDateTime dueDate);
}
