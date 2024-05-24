package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordInfoDto;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import com.consultorioapp.pacientes_api.model.Patient;

import java.util.List;

public interface IRecordService {
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
    List<MedicalRecordDto> getRecords();
    List<MedicalRecordDto> getRecordsByDni(String String);
    List<MedicalRecordDto> getRecordsByLastName(String lastname);
    List<MedicalRecordDto> getRecordsByFullName(String fullName);
    MedicalRecordDetailsDto getRecordDetails(Long id);
    Patient updatePatient(Long recordId, Patient patient);
    MedicalRecord updateRecordInfo(MedicalRecordInfoDto newRecordData);
    boolean deleteRecordById(Long recordId);
//    void addComment(MedicalRecord medicalRecord, String comment);
//    void deleteComment(MedicalRecord medicalRecord, String comment);
//    void updateComment(MedicalRecord medicalRecord, String comment);
}
