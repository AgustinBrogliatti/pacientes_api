package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(UserType.DOCTOR)

@NoArgsConstructor
@Getter
@Setter
public class Doctor extends User {
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public Doctor(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }

    public String getFullName() {
        return this.getName() + " " + this.getLastname();
    }
}