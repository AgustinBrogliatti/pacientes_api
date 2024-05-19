package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordInfoDto;
import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repositories.PatientRepository;
import com.consultorioapp.pacientes_api.repositories.RecordRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecordServiceImpl implements IRecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    public Patient CreatePatientService(Patient patient) {
        return patientServiceImpl.createPatient(patient);
    }

    @Override
    @Transactional
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        Patient patientData = medicalRecord.getPatient();
        Patient patient = CreatePatientService(patientData);
        medicalRecord.setPatient(patient);
        return recordRepository.save(medicalRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordDetailsDto getRecordDetails(Long id) {
        Optional<MedicalRecord> recordOptional = recordRepository.findById(id);
        if (recordOptional.isEmpty()) {
            throw new IllegalArgumentException("Registro médico no encontrado. ID: " + id);
        }
        return recordRepository.findDetails(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecords() {
        List<MedicalRecordDto> records = recordRepository.findAllPreview();
        if (records.isEmpty()) {
            throw new NoSuchElementException("No se encontraron registros médicos.");
        }
        return records;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByDni(String dni) {
        if (dni == null) {
            throw new IllegalArgumentException("El DNI del paciente no puede ser nulo.");
        }
        List<MedicalRecordDto> records = recordRepository.findByDniStartsWith(dni);
        if (records.isEmpty()) {
            throw new NoSuchElementException("No se encontraron registros médicos para el DNI: " + dni);
        }
        return records;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByLastName(String lastname) {
        if (lastname == null) {
            throw new IllegalArgumentException("El apellido ingresado no puede ser nulo.");
        }
        List<MedicalRecordDto> records = recordRepository.findByLastName(lastname);
        if (records.isEmpty()) {
            throw new NoSuchElementException("No se encontraron registros médicos para el apellido: " + lastname);
        }
        return records;
    }
    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByFullName(String fullName) {
        if (fullName == null) {
            throw new IllegalArgumentException("El fullname ingresado no puede ser nulo.");
        }
        List<MedicalRecordDto> records = recordRepository.findByPatientFullNameStartsWith(fullName);
        if (records.isEmpty()) {
            throw new NoSuchElementException("No se encontraron registros médicos para el nombre completo: " + fullName);
        }
        return records;
    }

    @Override
    @Transactional
    public Patient updatePatient(Long recordId, Patient patient) {
        Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(recordId);
        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord medicalRecord = optionalMedicalRecord.get();
            medicalRecord.setPatient(patient);
            recordRepository.save(medicalRecord);
            return patient;
        } else {
            throw new NoSuchElementException("Registro médico no encontrado id: " + recordId);
        }
    }

    @Override
    @Transactional
    public MedicalRecord updateRecordInfo(MedicalRecordInfoDto newRecordData) {
        Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(newRecordData.getId());
        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord existingRecord = optionalMedicalRecord.get();

            existingRecord.setHealthInsurances(newRecordData.getHealthInsurances());
            existingRecord.setPreviousHistory(newRecordData.getPreviousHistory());
            existingRecord.setAllergies(newRecordData.getAllergies());
            existingRecord.setMedicines(newRecordData.getMedicines());

            if (newRecordData.getDoctorId() != null) {
                Doctor newDoctor = (Doctor) userRepository.findById(Long.parseLong(newRecordData.getDoctorId()))
                        .orElseThrow(() -> new NoSuchElementException("Doctor no encontrado ID: " + newRecordData.getDoctorId()));
                existingRecord.setDoctor(newDoctor);
            }

            return recordRepository.save(existingRecord);
        } else {
            throw new NoSuchElementException("Medical Record not found with ID: " + newRecordData.getId());
        }
    }



}
