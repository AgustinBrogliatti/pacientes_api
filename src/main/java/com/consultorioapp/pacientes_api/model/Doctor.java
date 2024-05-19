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

    public Doctor(String name, String lastname, String username, String password, Room room) {
        super(name, lastname, username, password);
        this.room = room;
    }

    public Doctor(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }

    public void addCommentToMedicalRecord(MedicalRecord medicalRecord, Comment comment) {
        medicalRecord.addComment(comment);
    }

    public void removeCommentFromMedicalRecord(MedicalRecord medicalRecord, Comment comment) {
        medicalRecord.removeComment(comment);
    }
}