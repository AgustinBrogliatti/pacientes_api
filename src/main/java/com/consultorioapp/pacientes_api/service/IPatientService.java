package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Patient;

import java.util.Date;

public interface IPatientService {
    Patient createPatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email);

    Patient updatePatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email);

    Patient findPatientById(Long id);

    void deletePatient(Long dni);
}
