package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repository.PatientRepository;
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
        try {
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
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear el paciente: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public Patient updatePatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email) {
        Patient patient = patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        if (name != null && !name.equals(patient.getName())) {
            patient.setName(name);
        }
        if (lastname != null && !lastname.equals(patient.getLastname())) {
            patient.setLastname(lastname);
        }
        if (birthDate != null && !birthDate.equals(patient.getBirthDate())) {
            patient.setBirthDate(birthDate);
        }
        if (phoneNumber != null && !phoneNumber.equals(patient.getPhoneNumber())) {
            patient.setPhoneNumber(phoneNumber);
        }
        if (address != null && !address.equals(patient.getAddress())) {
            patient.setAddress(address);
        }
        if (email != null && !email.equals(patient.getEmail())) {
            patient.setEmail(email);
        }

        return patientRepository.save(patient);
    }


    @Override
    @Transactional
    public void deletePatient(Long dni) {
        Patient patient = patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        patientRepository.delete(patient);
    }
}
