package com.consultorioapp.pacientes_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor extends User {
    private ConsultingRoom room;

    public Doctor(String name, String lastname, String username, String password, ConsultingRoom room) {
        super(name, lastname, username, password); // Llama al constructor de User
        this.room = room;
    }

    public void getLine() {
        //TODO
    }

    public void getRecords() {
        //TODO
    }
}