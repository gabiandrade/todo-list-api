package com.andrade.todo_list_api.service;

import com.andrade.todo_list_api.dto.TaskDTO;
import com.andrade.todo_list_api.dto.TaskResponse;
import com.andrade.todo_list_api.domain.Task;
import com.andrade.todo_list_api.domain.User;
import com.andrade.todo_list_api.enums.Status;
import com.andrade.todo_list_api.repository.TaskRepository;
import com.andrade.todo_list_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private LocalDateTime parseDueDate(String s) {
        if (s == null) return null;
        try {
            return LocalDateTime.parse(s);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dueDate must be ISO-8601 e.g. 2025-12-01T15:30");
        }
    }

    private TaskResponse toResponse(Task t) {
        TaskResponse r = new TaskResponse();
        r.setId(t.getId());
        r.setTitle(t.getTitle());
        r.setDescription(t.getDescription());
        r.setDueDate(t.getDueDate());
        r.setStatus(t.getStatus().name());
        r.setPublic(t.isPublic());
        r.setOwnerId(t.getOwner() != null ? t.getOwner().getId() : null);
        r.setParticipantIds(t.getParticipants().stream().map(User::getId).collect(Collectors.toList()));
        return r;
    }

    // list tasks visible to user: public + owner own + tasks where user is participant
    public List<TaskResponse> listVisible(Long userId) {
        List<Task> all = taskRepository.findAll();
        return all.stream().filter(t -> {
            if (t.isPublic()) return true;
            if (t.getOwner() != null && t.getOwner().getId().equals(userId)) return true;
            return t.getParticipants().stream().anyMatch(u -> u.getId().equals(userId));
        }).map(this::toResponse).collect(Collectors.toList());
    }

    public TaskResponse create(TaskDTO dto) {
        // validate owner exists
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "owner not found"));

        Task t = new Task();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setDueDate(parseDueDate(dto.getDueDate()));
        t.setStatus(Status.valueOf(dto.getStatus()));
        t.setPublic(dto.isPublic());
        t.setOwner(owner);

        // add participants if provided
        if (dto.getParticipantIds() != null) {
            for (Long pid : dto.getParticipantIds()) {
                userRepository.findById(pid).ifPresent(t.getParticipants()::add);
            }
        }

        // check conflicts
        if (t.getDueDate() != null) {
            for (Long pid : t.getParticipants().stream().map(User::getId).toList()) {
                List<Task> userTasks = taskRepository.findByOwnerIdOrParticipantsId(pid, pid);
                for (Task existing : userTasks) {
                    if (existing.getDueDate() != null && existing.getDueDate().equals(t.getDueDate())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito de agenda para usuário " + pid);
                    }
                }
            }
            // also check owner
            List<Task> ownerTasks = taskRepository.findByOwnerIdOrParticipantsId(owner.getId(), owner.getId());
            for (Task existing : ownerTasks) {
                if (existing.getDueDate() != null && existing.getDueDate().equals(t.getDueDate())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito de agenda para owner");
                }
            }
        }

        Task saved = taskRepository.save(t);
        return toResponse(saved);
    }

    public TaskResponse getById(Long id, Long requesterId) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));
        // visibility check
        if (!t.isPublic()) {
            boolean ok = (t.getOwner() != null && t.getOwner().getId().equals(requesterId))
                    || t.getParticipants().stream().anyMatch(u -> u.getId().equals(requesterId));
            if (!ok) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not allowed to view task");
        }
        return toResponse(t);
    }

    public TaskResponse update(Long id, Long requesterId, TaskDTO dto) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));

        // only owner can update
        if (t.getOwner() == null || !t.getOwner().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "only owner can update");
        }

        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setDueDate(parseDueDate(dto.getDueDate()));
        t.setStatus(Status.valueOf(dto.getStatus()));
        t.setPublic(dto.isPublic());

        // replace participants
        t.getParticipants().clear();
        if (dto.getParticipantIds() != null) {
            for (Long pid : dto.getParticipantIds()) {
                userRepository.findById(pid).ifPresent(t.getParticipants()::add);
            }
        }

        // conflict check similar to create
        if (t.getDueDate() != null) {
            for (Long pid : t.getParticipants().stream().map(User::getId).toList()) {
                List<Task> userTasks = taskRepository.findByOwnerIdOrParticipantsId(pid, pid);
                for (Task existing : userTasks) {
                    if (!existing.getId().equals(t.getId()) && existing.getDueDate() != null && existing.getDueDate().equals(t.getDueDate())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito de agenda para usuário " + pid);
                    }
                }
            }
            // owner
            List<Task> ownerTasks = taskRepository.findByOwnerIdOrParticipantsId(t.getOwner().getId(), t.getOwner().getId());
            for (Task existing : ownerTasks) {
                if (!existing.getId().equals(t.getId()) && existing.getDueDate() != null && existing.getDueDate().equals(t.getDueDate())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflito de agenda para owner");
                }
            }
        }

        Task saved = taskRepository.save(t);
        return toResponse(saved);
    }

    public void delete(Long id, Long requesterId) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));
        if (t.getOwner() == null || !t.getOwner().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "only owner can delete");
        }
        taskRepository.delete(t);
    }

    public TaskResponse share(Long id, Long requesterId, java.util.List<Long> participantIds) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));
        if (t.getOwner() == null || !t.getOwner().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "only owner can share");
        }
        for (Long pid : participantIds) {
            userRepository.findById(pid).ifPresent(u -> {
                if (!t.getParticipants().contains(u)) t.getParticipants().add(u);
            });
        }
        Task saved = taskRepository.save(t);
        return toResponse(saved);
    }
}
