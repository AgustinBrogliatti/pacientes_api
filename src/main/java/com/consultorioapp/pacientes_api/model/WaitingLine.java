package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "patients_waiting")
@Data
@NoArgsConstructor
public class WaitingLine {
    @Id
    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName="patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "room", referencedColumnName = "room_id")
    private Room room;

    @Transient
    private List<Patient> patients;

    public void assignPatient(Patient patient) {
        this.patients.add(patient);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public void reorderPatients(List<Patient> patientsOrder) {
        this.patients.clear();
        this.patients.addAll(patientsOrder);
    }

    public void empty() {
        this.patients.clear();
    }
}