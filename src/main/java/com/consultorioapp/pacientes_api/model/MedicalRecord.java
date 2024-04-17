package com.consultorioapp.pacientes_api.model;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private Doctor doctor;
    @Transient
    private List<String> healthInsurances;
    private String previousHistory;
    private String history;
    @Transient
    private List<String> allergies;
    @Transient
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