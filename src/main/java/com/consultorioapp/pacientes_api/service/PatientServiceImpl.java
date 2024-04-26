package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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
    public Patient updatePatient(Long dni, String name, String lastname, Date birthDate, String phoneNumber, String address, String email) {
        Patient patient = patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        try {
            Field[] fields = Patient.class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Object newValue = null;

                switch (fieldName) {
                    case "name":
                        newValue = name;
                        break;
                    case "lastname":
                        newValue = lastname;
                        break;
                    case "birthDate":
                        newValue = birthDate;
                        break;
                    case "phoneNumber":
                        newValue = phoneNumber;
                        break;
                    case "address":
                        newValue = address;
                        break;
                    case "email":
                        newValue = email;
                        break;
                    default:
                        continue;
                }

                if (newValue != null && !newValue.equals(field.get(patient))) {
                    field.setAccessible(true);
                    field.set(patient, newValue);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al actualizar el paciente: " + e.getMessage());
        }

        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con el ID proporcionado"));
    }

    @Override
    @Transactional
    public void deletePatient(Long dni) {
        Patient patient = patientRepository.findById(dni)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        patientRepository.delete(patient);
    }
}
