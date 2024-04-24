package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.repository.UserRepository;
import org.junit.Assert;
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

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateSecretary() {
        roomService.createRoom("Sarmiento");
        Secretary newSecretary = userService.createSecretary("Secretaria", "Ficticia", "username", "password");

        Secretary savedSecretary = (Secretary) userRepository.findById(newSecretary.getId()).orElse(null);
        Assert.assertNotNull(savedSecretary);
        Assert.assertEquals("Secretaria", savedSecretary.getName());
        Assert.assertEquals("Ficticia", savedSecretary.getLastname());
        Assert.assertEquals("username", savedSecretary.getUsername());
        Assert.assertEquals("password", savedSecretary.getPassword());
    }

    Long roomId = 1L;
    @Test
    public void testCreateDoctor() {
        Doctor newDoctor = userService.createDoctor("Javier", "Moreno", "javier@gmail.com", "password", roomId);

        Doctor savedDoctor = (Doctor) userRepository.findById(newDoctor.getId()).orElse(null);
        Assert.assertNotNull(savedDoctor);
        Assert.assertEquals("Javier", savedDoctor.getName());
        Assert.assertEquals("Moreno", savedDoctor.getLastname());
        Assert.assertEquals("javier@gmail.com", savedDoctor.getUsername());
        Assert.assertEquals("password", savedDoctor.getPassword());
        Assert.assertEquals(roomId, savedDoctor.getRoom().getId());
    }
}
