package com.andrade.todo_list_api.repository;

import com.andrade.todo_list_api.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
