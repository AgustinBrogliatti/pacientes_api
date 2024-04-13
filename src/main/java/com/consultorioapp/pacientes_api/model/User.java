package com.consultorioapp.pacientes_api.model;

import lombok.Data;


@Data
public abstract class User {
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