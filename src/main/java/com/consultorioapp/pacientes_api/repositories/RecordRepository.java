package com.consultorioapp.pacientes_api.repositories;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<MedicalRecord, Long> {
    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
            " FROM MedicalRecord" +
            " m JOIN m.patient p" +
            " JOIN m.doctor d")
    List<MedicalRecordDto> findAllPreview();

    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto(m.id, d.name, p.dni, p.name, p.lastname, p.birthDate, p.phoneNumber, p.email, p.address, m.healthInsurances, m.allergies, m.medicines, m.previousHistory)" +
            " FROM MedicalRecord m" +
            " JOIN m.patient p" +
            " JOIN m.doctor d" +
            " WHERE m.id = :recordId")
    MedicalRecordDetailsDto findDetails(@Param("recordId") Long recordId);

//    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
//            " FROM MedicalRecord m" +
//            " JOIN m.patient p" +
//            " JOIN m.doctor d" +
//            " WHERE STR(p.dni) LIKE :dni%")
//    List<MedicalRecordDto> findByDniStartsWith(String dni);
//
//    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
//            " FROM MedicalRecord m" +
//            " JOIN m.patient p" +
//            " JOIN m.doctor d" +
//            " WHERE LOWER(p.lastname) LIKE LOWER(:lastname)%")
//    List<MedicalRecordDto> findByPatientLastnameStartsWith(String lastname);
//
//    @Query("SELECT new com.consultorioapp.pacientes_api.Dto.MedicalRecordDto(m.id, p.dni, p.lastname, p.name, m.healthInsurances, p.phoneNumber, d.name)" +
//            " FROM MedicalRecord m" +
//            " JOIN m.patient p" +
//            " JOIN m.doctor d" +
//            " WHERE CONCAT(p.lastname, ', ', p.name) LIKE :fullname%")
//    List<MedicalRecordDto> findByPatientFullNameStartsWith(String fullName);

    List<MedicalRecord> findByPatientDni(Long dni);
    MedicalRecord deleteByPatientDni(Long dni);
}
