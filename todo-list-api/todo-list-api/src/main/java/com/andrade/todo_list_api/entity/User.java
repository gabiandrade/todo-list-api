package com.andrade.todo_list_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// entidade é uma classe que moldamos pro db.
// em entidade nao utilizamos o @Data
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private Long id; //// mantemos o id do DummyJSON para facilitar o seed
    private String firstName;
    private String lastName;
    private String email;
}
