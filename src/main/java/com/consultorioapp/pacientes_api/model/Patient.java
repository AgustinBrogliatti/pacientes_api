package com.consultorioapp.pacientes_api.model;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@Data
public class Patient {
    @Id
    @Column(name = "patient_id", nullable = false)
    private Long dni;

    private String name;
    private String lastname;
    private Date birthDate;
    private String phoneNumber;
    private String address;
    private String email;

    public Patient(Long dni, String name, String lastname, Date birthDate, String phoneNumber) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public MedicalRecord getMedicalRecord() {
        //TODO
        return null;
    }
}
