package com.andrade.todo_list_api.repository;

import com.andrade.todo_list_api.domain.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
}
