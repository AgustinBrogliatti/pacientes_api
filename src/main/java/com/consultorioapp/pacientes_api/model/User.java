package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String lastname;
    private String username;
    private String password;

    public User(String name, String lastname, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }
}
