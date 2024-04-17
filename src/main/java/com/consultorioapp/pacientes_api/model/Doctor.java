package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@Getter
@Setter
public class Doctor extends User {
    @ManyToOne
    @JoinColumn(name = "room", referencedColumnName = "room_id")
    private Room room;

    public Doctor(String name, String lastname, String username, String password, Room room) {
        super(name, lastname, username, password);
        this.room = room;
    }

    public void getLine() {
        //TODO
    }

    public void getRecords() {
        //TODO
    }
}