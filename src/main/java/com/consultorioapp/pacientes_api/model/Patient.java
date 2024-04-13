package com.consultorioapp.pacientes_api.model;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient {
    private int dni;
    private String name;
    private String lastname;
    private Date birthDate;
    private String phoneNumber;
    private String address;
    private String email;

    public Patient(int dni, String name, String lastname, Date birthDate, String phoneNumber) {
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
