package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.model.MedicalRecord;

import java.util.List;

public interface IRecordService {
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
    MedicalRecord getRecordById(Long id);
    MedicalRecordDetailsDto getRecordDetails(Long id);
    List<MedicalRecordDto> getRecords();
    List<MedicalRecordDto> getRecordsByDni(String String);
    List<MedicalRecordDto> getRecordsByLastName(String lastname);
    List<MedicalRecordDto> getRecordsByFullName(String fullName);

//    Patient updatePatient(Patient patient);
//    MedicalRecord updateRecord(MedicalRecord medicalRecord);
//    MedicalRecord deleteRecordByDni(Long dni);
//    void addComment(MedicalRecord medicalRecord, String comment);
//    void deleteComment(MedicalRecord medicalRecord, String comment);
//    void updateComment(MedicalRecord medicalRecord, String comment);

}
