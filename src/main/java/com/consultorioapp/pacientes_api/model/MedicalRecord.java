package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "record")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "dni", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @Column(name = "health_insurances", length = 100)
    private String healthInsurances;

    @Column(name = "previous_history", length = 2000)
    private String previousHistory;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "allergies", length = 500)
    private String allergies;

    @Column(name = "medicines", length = 500)
    private String medicines;

    public MedicalRecord(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void addAllergy(String allergy) {
        if (this.allergies == null) {
            this.allergies = allergy;
        } else {
            this.allergies += "; " + allergy;
        }
    }

    public void deleteAllergy(String allergy) {
        if (this.allergies != null) {
            String[] allergyArray = this.allergies.split("; ");
            List<String> updatedAllergies = new ArrayList<>();
            for (String a : allergyArray) {
                if (!a.equals(allergy)) {
                    updatedAllergies.add(a);
                }
            }
            this.allergies = String.join("; ", updatedAllergies);
        }
    }

    public void addMedicine(String medicine) {
        if (this.medicines == null) {
            this.medicines = medicine;
        } else {
            this.medicines += "; " + medicine;
        }
    }

    public void deleteMedicine(String medicine) {
        if (this.medicines != null) {
            String[] medicineArray = this.medicines.split("; ");
            List<String> updatedMedicines = new ArrayList<>();
            for (String m : medicineArray) {
                if (!m.equals(medicine)) {
                    updatedMedicines.add(m);
                }
            }
            this.medicines = String.join("; ", updatedMedicines);
        }
    }
}