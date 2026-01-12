package com.andrade.todo_list_api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private Long id;
    /// / mantemos o id do DummyJSON para facilitar o seed
    private String firstName;
    private String lastName;
    private String email;
}
