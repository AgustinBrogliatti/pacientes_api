package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;

import java.util.Date;

public interface IPatientService {
    Patient createPatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email);

    Patient createPatient(Patient patientData);

    Patient updatePatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email);

    Patient findPatientById(Long id);
}
