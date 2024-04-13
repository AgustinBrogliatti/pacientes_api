package com.consultorioapp.pacientes_api.model;
import java.util.List;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Patient {
    private int dni;
    private String name;
    private String lastname;
    private List<HealthInsurance> healthInsurances;
    private String dr;
    private String address;
    private String birthDate;
    private String phoneNumber;
    private String email;

    public Patient(int dni, String name, String lastname, String dr, String birthDate, String phoneNumber) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.dr = dr;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
