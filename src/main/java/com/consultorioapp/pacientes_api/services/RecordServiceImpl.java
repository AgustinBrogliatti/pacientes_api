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
        try {
            Patient patientData = medicalRecord.getPatient();
            Patient patient = CreatePatientService(patientData);
            medicalRecord.setPatient(patient);
            return recordRepository.save(medicalRecord);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecords() {
        try {
            return recordRepository.findAllPreview();
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByDni(String dni) {
        try {
            return recordRepository.findByDniStartsWith(dni);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByLastName(String lastname) {
        try {
            return recordRepository.findByLastName(lastname);
        } catch(Exception e) {throw e;}

    }
    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordDto> getRecordsByFullName(String fullName) {
        try {
            return recordRepository.findByPatientFullNameStartsWith(fullName);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordDetailsDto getRecordDetails(Long id) {
        try {
            return recordRepository.findDetails(id);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional
    public Patient updatePatient(Long recordId, Patient patient) {
        try {
            Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(recordId);
            MedicalRecord medicalRecord = optionalMedicalRecord.get();
            medicalRecord.setPatient(patient);
            recordRepository.save(medicalRecord);
            return patient;
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional
    public MedicalRecord updateRecordInfo(MedicalRecordInfoDto newRecordData) {
        try {
            Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(newRecordData.getId());
            MedicalRecord existingRecord = optionalMedicalRecord.get();

            existingRecord.setHealthInsurances(newRecordData.getHealthInsurances());
            existingRecord.setPreviousHistory(newRecordData.getPreviousHistory());
            existingRecord.setAllergies(newRecordData.getAllergies());
            existingRecord.setMedicines(newRecordData.getMedicines());


            Doctor newDoctor = (Doctor) userRepository.findById(Long.parseLong(newRecordData.getDoctorId()))
                    .orElseThrow(() -> new NoSuchElementException("Doctor no encontrado ID: " + newRecordData.getDoctorId()));
            existingRecord.setDoctor(newDoctor);

            return recordRepository.save(existingRecord);
        } catch(Exception e) {throw e;}
    }
    @Override
    @Transactional
    public boolean deleteRecordById(Long recordId) {
        try {
            Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(recordId);
            MedicalRecord medicalRecord = optionalMedicalRecord.get();

            Patient patient = medicalRecord.getPatient();
            patientRepository.deleteById(patient.getDni());

            recordRepository.delete(medicalRecord);
            return true;
        } catch(Exception e) {throw e;}
    }
}
