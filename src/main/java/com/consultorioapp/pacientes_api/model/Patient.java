package com.consultorioapp.pacientes_api.model;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @Column(name = "dni")
    private Long dni;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(length = 100)
    private String address;

    @Column(unique = true, length = 50)
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
