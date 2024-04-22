package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Dr")

@AllArgsConstructor
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

    public void getRecords() {
        //TODO implementar service y repository
    }

    public void addCommentToMedicalRecord(MedicalRecord medicalRecord, Comment comment) {
        medicalRecord.addComment(comment);
    }

    public void removeCommentFromMedicalRecord(MedicalRecord medicalRecord, Comment comment) {
        medicalRecord.removeComment(comment);
    }
}