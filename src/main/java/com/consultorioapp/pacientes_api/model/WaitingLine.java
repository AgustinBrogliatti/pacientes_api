package com.consultorioapp.pacientes_api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WaitingLine {
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