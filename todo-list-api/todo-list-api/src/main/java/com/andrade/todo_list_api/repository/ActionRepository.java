package com.andrade.todo_list_api.repository;

import com.andrade.todo_list_api.domain.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
