package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;

import java.util.Date;

public interface IPatientService {
    Patient createPatient(Patient patient);
    Patient updatePatient(Patient newPatientData);
}
