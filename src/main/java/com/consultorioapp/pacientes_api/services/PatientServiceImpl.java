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
    public Patient createPatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email) {
        Optional<Patient> existingPatient = patientRepository.findById(dni);
        if (existingPatient.isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente con el DNI proporcionado");
        }

        if (email != null && patientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email proporcionado ya está asociado a otro paciente");
        }

        Patient patient = new Patient(dni, name, lastname, birthDate, phoneNumber);
        patient.setAddress(address);
        patient.setEmail(email);
        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public Patient createPatient(Patient patientData) {
        return createPatient(
                patientData.getDni(),
                patientData.getName(),
                patientData.getLastname(),
                patientData.getBirthDate(),
                patientData.getPhoneNumber(),
                patientData.getAddress(),
                patientData.getEmail());
    }

    @Override
    @Transactional
    public Patient updatePatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email) {
        Patient patient = patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        if (name != null) {
            patient.setName(name);
        }
        if (lastname != null) {
            patient.setLastname(lastname);
        }
        if (birthDate != null) {
            patient.setBirthDate(birthDate);
        }
        if (phoneNumber != null) {
            patient.setPhoneNumber(phoneNumber);
        }
        if (address != null) {
            patient.setAddress(address);
        }
        if (email != null) {
            patient.setEmail(email);
        }

        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public Patient findPatientById(Long dni) {
        return patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con el ID proporcionado"));
    }


}
