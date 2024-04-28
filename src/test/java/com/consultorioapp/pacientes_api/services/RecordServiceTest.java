package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RecordRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
public class RecordServiceTest {
    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoomServiceImpl roomServiceImpl;
    @Autowired
    private RecordServiceImpl recordServiceImpl;

    public Patient CreatePatientService(Patient patient) {
        return patientServiceImpl.createPatient(patient);
    }

    public Doctor CreateDoctor(Long roomId, String docName) {
        return (Doctor) userServiceImpl.createUser(docName, "Moreno", docName, "password", UserType.DOCTOR, roomId);
    }

    public Room CreateRoom() {
        return roomServiceImpl.createRoom("Rivadavia");
    }

    public MedicalRecord CreateRecord(Long dni, String email, String docName) {
        Doctor newDoctor = CreateDoctor(1L, docName);
        templatePatient.setDni(dni);
        templatePatient.setEmail(email);
        Patient patientData = templatePatient;

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patientData);
        medicalRecord.setHealthInsurances("OSDE");
        medicalRecord.setAllergies("Acaros");
        medicalRecord.setDoctor(newDoctor);
        medicalRecord.setMedicines("Ibuprofeno");
        medicalRecord.setPreviousHistory("pre-historial");
        return recordServiceImpl.createMedicalRecord(medicalRecord);
    }

    Patient templatePatient = new Patient(
            123456789L,
            "Juan",
            "Perez",
            new Date(631152000000L),
            "123456789",
            "Calle 123",
            "juan@example.com"
    );

    @Test
    public void testCreateMedicalRecord() {
        Room newRoom = CreateRoom();
        MedicalRecord newRecord  = CreateRecord(1111L, "email1","user-doc1");
        MedicalRecord recoveredRecord = recordServiceImpl.getRecordById(newRecord.getId());

        Assert.assertNotNull(recoveredRecord);
        Assert.assertEquals(newRecord.getPatient().getDni(), recoveredRecord.getPatient().getDni());
        Assert.assertEquals(newRecord.getDoctor().getUsername(), recoveredRecord.getDoctor().getUsername());
        Assert.assertEquals(newRecord.getHealthInsurances(), recoveredRecord.getHealthInsurances());
        Assert.assertEquals(newRecord.getAllergies(), recoveredRecord.getAllergies());
        Assert.assertEquals(newRecord.getMedicines(), recoveredRecord.getMedicines());
        Assert.assertEquals(newRecord.getPreviousHistory(), recoveredRecord.getPreviousHistory());
    }

    @Test
    public void testGetRecordById() {
        MedicalRecord newRecord  = CreateRecord(2222L, "email2","user-doc2");
        MedicalRecord retrievedRecord = recordServiceImpl.getRecordById(newRecord.getId());

        Assert.assertNotNull(retrievedRecord);
        Assert.assertEquals(newRecord.getId(), retrievedRecord.getId());
    }

    @Test
    public void testGetRecordDetails() {
        MedicalRecord newRecord  = CreateRecord(3333L, "email3","user-doc3");
        MedicalRecordDetailsDto recordDetails = recordServiceImpl.getRecordDetails(newRecord.getId());

        Assert.assertNotNull(recordDetails);
        Assert.assertNotNull(newRecord.getId().toString(), recordDetails.getId());
        Assert.assertNotNull(newRecord.getPatient().getDni().toString(), recordDetails.getPatientDni());
        Assert.assertNotNull(newRecord.getDoctor().getName(), recordDetails.getDoctorName());
        Assert.assertNotNull(newRecord.getPatient().getName(), recordDetails.getPatientName());
        Assert.assertNotNull(newRecord.getPatient().getLastname(), recordDetails.getPatientLastname());
        Assert.assertNotNull(newRecord.getPatient().getBirthDate().toString(), recordDetails.getPatientBirthdate());
        Assert.assertNotNull(newRecord.getPatient().getPhoneNumber(), recordDetails.getPatientPhone());
        Assert.assertNotNull(newRecord.getPatient().getEmail(), recordDetails.getPatientEmail());
        Assert.assertNotNull(newRecord.getPatient().getAddress(), recordDetails.getPatientAddress());
        Assert.assertNotNull(newRecord.getHealthInsurances(), recordDetails.getHealthInsurances());
        Assert.assertNotNull(newRecord.getAllergies(), recordDetails.getAllergies());
        Assert.assertNotNull(newRecord.getMedicines(), recordDetails.getMedicines());
        Assert.assertNotNull(newRecord.getPreviousHistory(), recordDetails.getPreviousHistory());
    }

    @Test
    public void testGetRecords() {
        List<MedicalRecordDto> records = recordServiceImpl.getRecords();

        Assert.assertNotNull(records);
        Assert.assertEquals(3, records.size());

        for (int i = 0; i < records.size(); i++) {
            MedicalRecordDto record = records.get(i);
            Assert.assertEquals("user-doc" + (i+1), record.getDoctorName());
            Assert.assertEquals(Long.valueOf(i+1), record.getId());
            Assert.assertEquals(Long.valueOf((i+1)*1111L), record.getPatientDni());
        }

        for (MedicalRecordDto recordDto   : records) {
            System.out.println(recordDto.getDoctorName());
            System.out.println(recordDto.getPatientDni());
        }
    }

}
