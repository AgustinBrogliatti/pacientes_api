package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.repositories.PatientRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private PatientRepository patientRepository;

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
    public void testCreatePatient() {
        templatePatient.setEmail("test1@email.com");
        templatePatient.setDni(11111111L);
        Patient newPatient = patientService.createPatient(templatePatient);

        Patient retrievedPatient = patientRepository.findById(newPatient.getDni()).orElse(null);
        Assert.assertNotNull(retrievedPatient);

        Assert.assertEquals(newPatient.getDni(), retrievedPatient.getDni());
        Assert.assertEquals(newPatient.getName(), retrievedPatient.getName());
        Assert.assertEquals(newPatient.getLastname(), retrievedPatient.getLastname());
        Assert.assertEquals("1989-12-31", new SimpleDateFormat("yyyy-MM-dd").format(newPatient.getBirthDate()));
        Assert.assertEquals(newPatient.getPhoneNumber(), retrievedPatient.getPhoneNumber());
        Assert.assertEquals(newPatient.getAddress(), retrievedPatient.getAddress());
        Assert.assertEquals(newPatient.getEmail(), retrievedPatient.getEmail());
    }

    @Test
    public void testCreatePatient_DuplicateDNI() {
        patientService.createPatient(templatePatient);
        try {
            patientService.createPatient(templatePatient);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Ya existe un paciente con el DNI proporcionado", e.getMessage());
        }
    }

    @Test
    public void testCreatePatient_DuplicateEmail() {
        templatePatient.setDni(22222222L);
        try {
            patientService.createPatient(templatePatient);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El email proporcionado ya está asociado a otro paciente", e.getMessage());
        }
    }

    @Test
    public void testUpdatePatient() {
        templatePatient.setEmail("test2@email.com");
        templatePatient.setDni(33333333L);
        Patient existingPatient = patientService.createPatient(templatePatient);

        Patient newPatientData = new Patient();
        newPatientData.setDni(existingPatient.getDni());
        newPatientData.setName("Juanito");
        newPatientData.setLastname("Perez");
        newPatientData.setBirthDate(new Date(631152000000L));
        newPatientData.setPhoneNumber("987654321");
        newPatientData.setAddress("Avenida 456");
        newPatientData.setEmail("juanito@example.com");


        Patient updatedPatient = patientService.updatePatient(newPatientData);

        Assert.assertNotNull(updatedPatient);
        Assert.assertEquals(existingPatient.getDni(), updatedPatient.getDni());
        Assert.assertEquals(newPatientData.getName(), updatedPatient.getName());
        Assert.assertEquals(newPatientData.getLastname(), updatedPatient.getLastname());
        Assert.assertEquals(newPatientData.getPhoneNumber(), updatedPatient.getPhoneNumber());
        Assert.assertEquals(newPatientData.getAddress(), updatedPatient.getAddress());
        Assert.assertEquals(newPatientData.getEmail(), updatedPatient.getEmail());
    }



    @Test
    public void testUpdatePatient_NotFound() {
        templatePatient.setDni(123123L);
        try {
            patientService.updatePatient(templatePatient);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Paciente no encontrado", e.getMessage());
        }
    }
}
