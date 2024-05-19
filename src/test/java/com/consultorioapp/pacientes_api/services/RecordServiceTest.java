package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordInfoDto;
import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RecordRepository;
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
    private RecordRepository recordRepository;

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

    public Doctor CreateDoctor(String docName) {
        Room newRoom = CreateRoom(docName + "-room");
        Doctor newDoctor = new Doctor(docName, "Moreno", docName, "password");
        return userServiceImpl.createDoctor(newDoctor, newRoom.getId());
    }

    public Room CreateRoom(String name) {
        return roomServiceImpl.createRoom(name);
    }

    public MedicalRecord CreateRecord(Long dni, String email, String docName) {
        Doctor newDoctor = CreateDoctor(docName);
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
        MedicalRecord newRecord  = CreateRecord(1111L, "email1","user-doc1");
        MedicalRecord recoveredRecord = recordRepository.findById(newRecord.getId()).get();

        Assert.assertNotNull(recoveredRecord);
        Assert.assertEquals(newRecord.getPatient().getDni(), recoveredRecord.getPatient().getDni());
        Assert.assertEquals(newRecord.getDoctor().getUsername(), recoveredRecord.getDoctor().getUsername());
        Assert.assertEquals(newRecord.getHealthInsurances(), recoveredRecord.getHealthInsurances());
        Assert.assertEquals(newRecord.getAllergies(), recoveredRecord.getAllergies());
        Assert.assertEquals(newRecord.getMedicines(), recoveredRecord.getMedicines());
        Assert.assertEquals(newRecord.getPreviousHistory(), recoveredRecord.getPreviousHistory());
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
    public void testGetRecordsByDniStartWith() {
        String startDni = "123";
        CreateRecord(1234444L, "email4","user-doc4");
        CreateRecord(1235555L, "email5","user-doc5");
        CreateRecord(1236666L, "email6","user-doc6");

        List<MedicalRecordDto> records = recordServiceImpl.getRecordsByDni(startDni);

        Assert.assertNotNull(records);
        Assert.assertEquals(3, records.size());

        boolean found1234444 = false;
        boolean found1235555 = false;
        boolean found1236666 = false;

        for (MedicalRecordDto record : records) {
            String dniPrefix = String.valueOf(record.getPatientDni()).substring(0, 3);
            Assert.assertEquals("123", dniPrefix);

            if (record.getPatientDni() == 1234444L) {
                found1234444 = true;
            } else if (record.getPatientDni() == 1235555L) {
                found1235555 = true;
            } else if (record.getPatientDni() == 1236666L) {
                found1236666 = true;
            }
        }

        Assert.assertTrue(found1234444);
        Assert.assertTrue(found1235555);
        Assert.assertTrue(found1236666);
    }

    @Test
    public void testGetRecordsByDni() {
        CreateRecord(777777L, "email7","user-doc7");
        String dni = "777777";
        List<MedicalRecordDto> records = recordServiceImpl.getRecordsByDni(dni);

        Assert.assertNotNull(records);
        Assert.assertEquals(1, records.size());

        boolean foundRecord = false;

        for (MedicalRecordDto record : records) {
            if (record.getPatientDni() == 777777L) {
                foundRecord = true;
                break;
            }
        }

        Assert.assertTrue(foundRecord);
    }

    @Test
    public void testGetRecordsByLastnameStartWith() {
        String startLastname = "Gia";
        templatePatient.setLastname("Giacomini");
        CreateRecord(8888L, "email10","user-doc10");
        templatePatient.setLastname("Giacomo");
        CreateRecord(9999L, "email15","user-doc15");

        List<MedicalRecordDto> records = recordServiceImpl.getRecordsByLastName(startLastname);

        Assert.assertNotNull(records);
        Assert.assertEquals(2, records.size());

        boolean foundGiacomini = false;
        boolean foundGiacomo = false;

        for (MedicalRecordDto record : records) {
            String lastnamePrefix = String.valueOf(record.getPatientLastname()).substring(0, 3);
            Assert.assertEquals("Gia", lastnamePrefix);

            if (record.getPatientLastname().equals("Giacomini")) {
                foundGiacomini = true;
            }
            if (record.getPatientLastname().equals("Giacomo")) {
                foundGiacomo = true;
            }
        }

        Assert.assertTrue(foundGiacomini);
        Assert.assertTrue(foundGiacomo);
    }

    @Test
    public void testGetRecordsByFullnameStartWith() {
        templatePatient.setLastname("Brogliatti Briones");
        templatePatient.setName("Oreste Agustin");
        CreateRecord(1212L, "email12","user-doc12");

        String search = "Brogliatti Briones, Ore";
        List<MedicalRecordDto> records = recordServiceImpl.getRecordsByFullName(search);

        Assert.assertNotNull(records);
        Assert.assertEquals(1, records.size());

        boolean foundAgustin = false;

        for (MedicalRecordDto record : records) {
            String fullnamePrefix = String.valueOf(record.getPatientLastname()).substring(0, 10);
            Assert.assertEquals("Brogliatti", fullnamePrefix);

            String getFullNameReceived = record.getPatientLastname() + ", " + record.getPatientName();

            if (getFullNameReceived.equals("Brogliatti Briones, Oreste Agustin")) {
                foundAgustin = true;
            }
        }

        Assert.assertTrue(foundAgustin);
    }

    @Test
    public void testUpdatePatientRecord() {
        MedicalRecord newRecord = CreateRecord(1313L, "email13","user-doc13");
        String newName = "Agustin";
        String newEmail = "agustin@gmail.com";

        templatePatient.setName(newName);
        templatePatient.setEmail(newEmail);

        Patient newPatientData = templatePatient;

        Patient patientReceived = recordServiceImpl.updatePatient(newRecord.getId(), newPatientData);

        Assert.assertNotNull(patientReceived);
        Assert.assertEquals(newRecord.getPatient().getEmail(), "email13");
        Assert.assertEquals(patientReceived.getEmail(), newEmail);
        Assert.assertEquals(patientReceived.getName(), newName);
    }

    @Test
    public void testUpdateRecord() {
        MedicalRecord existRecord = CreateRecord(1414L, "email14", "user-doc14");
        String newDoctorUsername = "updated-doctor";
        String newHealthInsurances = "New Health Insurance";
        String newPreviousHistory = "New Previous History";
        String newAllergies = "New Allergies";
        String newMedicines = "New Medicines";

        Room newRoom = CreateRoom("new-room");
        Doctor newDoctor = CreateDoctor(newDoctorUsername);

        MedicalRecordInfoDto  newRecordData = new MedicalRecordInfoDto(
                existRecord.getId(),
                newDoctor.getId().toString(),
                newHealthInsurances,
                newPreviousHistory,
                newAllergies,
                newMedicines
        );

        MedicalRecord updatedRecord = recordServiceImpl.updateRecordInfo(newRecordData);

        Assert.assertEquals(newDoctorUsername, updatedRecord.getDoctor().getUsername());
        Assert.assertEquals(newHealthInsurances, updatedRecord.getHealthInsurances());
        Assert.assertEquals(newPreviousHistory, updatedRecord.getPreviousHistory());
        Assert.assertEquals(newAllergies, updatedRecord.getAllergies());
        Assert.assertEquals(newMedicines, updatedRecord.getMedicines());
    }


    @Test
    public void testGetRecords() {
        List<MedicalRecordDto> records = recordServiceImpl.getRecords();
        Assert.assertNotNull(records);

        for (int i = 0; i < records.size(); i++) {
            MedicalRecordDto record = records.get(i);
            Assert.assertEquals("user-doc" + (i+1), record.getDoctorName());
            Assert.assertEquals(Long.valueOf(i+1), record.getId());
            Assert.assertEquals(Long.valueOf((i+1)*1111L), record.getPatientDni());
        }
    }

}
