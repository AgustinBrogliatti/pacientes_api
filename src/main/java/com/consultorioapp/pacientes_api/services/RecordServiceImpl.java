package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordHealthDto;
import com.consultorioapp.pacientes_api.model.Comment;
import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import com.consultorioapp.pacientes_api.model.Patient;
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
    private UserRepository userRepository;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    public Patient CreatePatientService(Patient patient) {
        return patientServiceImpl.createPatient(patient);
    }
    public Patient UpdatePatientService(Patient patient) {
        return patientServiceImpl.updatePatient(patient);
    }

    public MedicalRecordDetailsDto convertToDto(MedicalRecord medicalRecord) {
        MedicalRecordDetailsDto dto = new MedicalRecordDetailsDto();
        dto.setId(medicalRecord.getId());
        dto.setDoctorId(medicalRecord.getDoctor().getId());
        dto.setDoctorName(medicalRecord.getDoctor().getName());
        dto.setPatient(medicalRecord.getPatient());
        dto.setDoctorId(medicalRecord.getDoctor().getId());
        dto.setHealthInsurances(medicalRecord.getHealthInsurances());
        dto.setPreviousHistory(medicalRecord.getPreviousHistory());
        dto.setAllergies(medicalRecord.getAllergies());
        dto.setMedicines(medicalRecord.getMedicines());
        return dto;
    }

    @Override
    @Transactional
    public
    MedicalRecordDetailsDto createMedicalRecord(MedicalRecord medicalRecord) {
        try {
            Patient patientData = medicalRecord.getPatient();
            Patient patient = CreatePatientService(patientData);
            medicalRecord.setPatient(patient);

            Long doctorId = medicalRecord.getDoctor().getId();
            Doctor doctor = (Doctor) userRepository.findById(doctorId)
                    .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado. ID: " + doctorId));
            medicalRecord.setDoctor(doctor);

            return convertToDto(recordRepository.save(medicalRecord));
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordPreviewDto> getRecords() {
        try {
            return recordRepository.findAllPreview();
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordPreviewDto> getRecordsByDni(String dni) {
        try {
            return recordRepository.findByDniStartsWith(dni);
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordPreviewDto> getRecordsByLastName(String lastname) {
        try {
            return recordRepository.findByLastName(lastname);
        } catch(Exception e) {throw e;}

    }
    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordPreviewDto> getRecordsByFullName(String fullName) {
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
            if(patient.getDni().equals(medicalRecord.getPatient().getDni())) {
                return UpdatePatientService(patient);
            };
            throw new IllegalArgumentException();
        } catch(Exception e) {throw e;}
    }

    @Override
    @Transactional
    public MedicalRecordDetailsDto updateRecordInfo(MedicalRecordHealthDto newRecordData) {
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

            return convertToDto(recordRepository.save(existingRecord));
        } catch(Exception e) {throw e;}
    }
    @Override
    @Transactional
    public boolean deleteRecordById(Long recordId) {
        try {
            Optional<MedicalRecord> optionalMedicalRecord = recordRepository.findById(recordId);
            MedicalRecord medicalRecord = optionalMedicalRecord.get();
            recordRepository.delete(medicalRecord);
            return true;
        } catch(Exception e) {throw e;}
    }

}
