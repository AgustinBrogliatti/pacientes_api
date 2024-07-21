package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(UserType.SUPERUSER)

@NoArgsConstructor
@Getter
@Setter
public class God extends User {
    public God(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }
}
