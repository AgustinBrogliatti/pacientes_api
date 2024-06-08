package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordHealthDto;
import com.consultorioapp.pacientes_api.model.Comment;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import com.consultorioapp.pacientes_api.model.Patient;

import java.util.List;

public interface IRecordService {
    MedicalRecordDetailsDto createMedicalRecord(MedicalRecord medicalRecord);
    List<MedicalRecordPreviewDto> getRecords();
    List<MedicalRecordPreviewDto> getRecordsByDni(String String);
    List<MedicalRecordPreviewDto> getRecordsByLastName(String lastname);
    List<MedicalRecordPreviewDto> getRecordsByFullName(String fullName);
    MedicalRecordDetailsDto getRecordDetails(Long id);
    Patient updatePatient(Long recordId, Patient patient);
    MedicalRecordDetailsDto updateRecordInfo(MedicalRecordHealthDto newRecordData);
    boolean deleteRecordById(Long recordId);
    MedicalRecord addComment(Long recordId, Comment comment);
//    void deleteComment(MedicalRecord medicalRecord, String comment);
//    void updateComment(MedicalRecord medicalRecord, String comment);
}
