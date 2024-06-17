package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional
    public Patient createPatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional
    public Patient updatePatient(Patient newPatientData) {
        try {
            return patientRepository.save(newPatientData);
        } catch(Exception e) {throw e;}
    }
}
