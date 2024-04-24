package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class createUserTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testCreateSecretary() {
        Secretary newSecretary = userService.createSecretary("Secretaria", "Ficticia", "username", "password");
    }

    Long roomId = 1L;
    @Test
    public void testCreateDoctor() {
        Doctor newDoctor = userService.createDoctor("Doctor", "Moreno", "username", "password", roomId);
    }
}
