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

    @OneToOne
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
        //comment.setMedicalRecord(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        //comment.setMedicalRecord(null);
    }
}