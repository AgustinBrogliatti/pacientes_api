package com.consultorioapp.pacientes_api.repositories;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<MedicalRecord, Long> {
    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
            " FROM MedicalRecord" +
            " m JOIN m.patient p" +
            " JOIN m.doctor d")
    List<MedicalRecordPreviewDto> findAllPreview();

    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto(m.id, d.id, d.name, p, m.healthInsurances, m.allergies, m.medicines, m.previousHistory)" +
            " FROM MedicalRecord m" +
            " JOIN m.patient p" +
            " JOIN m.doctor d" +
            " WHERE m.id = :recordId")
    MedicalRecordDetailsDto findDetails(@Param("recordId") Long recordId);

    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
            " FROM MedicalRecord m" +
            " JOIN m.patient p" +
            " JOIN m.doctor d" +
            " WHERE STR(p.dni) LIKE :dni%")
    List<MedicalRecordPreviewDto> findByDniStartsWith(String dni);

    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
            " FROM MedicalRecord m" +
            " JOIN m.patient p" +
            " JOIN m.doctor d" +
            " WHERE LOWER(p.lastname) LIKE LOWER(:lastname) || '%'")
    List<MedicalRecordPreviewDto> findByLastName(@Param("lastname") String lastname);

    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
            " FROM MedicalRecord m" +
            " JOIN m.patient p" +
            " JOIN m.doctor d" +
            " WHERE CONCAT(p.lastname, ', ', p.name) LIKE LOWER(:fullName) || '%'")
    List<MedicalRecordPreviewDto> findByPatientFullNameStartsWith(@Param("fullName") String fullName);
}
