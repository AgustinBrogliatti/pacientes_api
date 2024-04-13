package com.consultorioapp.pacientes_api.model;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MedicalRecord {
    private Patient patient;
    private Doctor doctor;
    private List<String> healthInsurances;
    private String previousHistory;
    private String history;
    private List<String> allergies;
    private List<String> medicines;

    public MedicalRecord(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void deleteAllergy(String allergy) {
        allergies.remove(allergy);
    }

    public void addMedicine(String medicine) {
        medicines.add(medicine);
    }

    public void deleteMedicine(String medicine) {
        medicines.remove(medicine);
    }
}