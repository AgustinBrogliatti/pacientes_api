package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional
    public Patient createPatient(Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(patient.getDni());
        if (existingPatient.isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente con el DNI proporcionado");
        }

        if (patient.getEmail() != null && patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("El email proporcionado ya está asociado a otro paciente");
        }

        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public Patient updatePatient(Patient newPatientData) {
        Patient patient = patientRepository.findById(newPatientData.getDni())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        return patientRepository.save(newPatientData);
    }

}
