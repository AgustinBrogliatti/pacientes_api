package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import com.consultorioapp.pacientes_api.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    Long roomId = 1L;

    @Test
    public void testCreateUserSecretary() {
        roomService.createRoom("Sarmiento");
        Secretary newUser = (Secretary) userService.createUser("Secretaria", "Ficticia", "username", "password", UserType.SECRETARY);

        Secretary savedSecretary = (Secretary) userRepository.findById(newUser.getId()).orElse(null);
        Assert.assertNotNull(savedSecretary);
        Assert.assertEquals("Secretaria", savedSecretary.getName());
        Assert.assertEquals("Ficticia", savedSecretary.getLastname());
        Assert.assertEquals("username", savedSecretary.getUsername());
        Assert.assertEquals("password", savedSecretary.getPassword());
    }

    @Test
    public void testCreateUserDoctor() {
        Doctor newDoctor = (Doctor) userService.createUser("Javier", "Moreno", "javier@gmail.com", "password", UserType.DOCTOR, roomId);

        Doctor savedDoctor = (Doctor) userRepository.findById(newDoctor.getId()).orElse(null);
        Assert.assertNotNull(savedDoctor);
        Assert.assertEquals("Javier", savedDoctor.getName());
        Assert.assertEquals("Moreno", savedDoctor.getLastname());
        Assert.assertEquals("javier@gmail.com", savedDoctor.getUsername());
        Assert.assertEquals("password", savedDoctor.getPassword());
        Assert.assertEquals(roomId, savedDoctor.getRoom().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithExistingUsername() {
        userService.createUser("John", "Doe", "johndoe", "password", UserType.DOCTOR, roomId);
        try {
            userService.createUser("Jane", "Doe", "johndoe", "password", UserType.SECRETARY);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El nombre de usuario ya está en uso", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDoctorWithInvalidRoomId() {
        try {
            userService.createUser("Juan", "Pérez", "juanperez", "password", UserType.DOCTOR, roomId);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El roomId proporcionado no es válido", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithInvalidUserType() {
        try {
            userService.createUser("Jane", "Doe", "janedoe", "password", "InvalidType");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Tipo de usuario no válido", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetDoctorById() {
        User user =  userService.createUser("John", "Doe", "meli", "password", UserType.SECRETARY);
        User retrievedUser = userService.getUserById(user.getId());

        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(user.getId(), retrievedUser.getId());
        Assert.assertEquals(user.getName(), retrievedUser.getName());
        Assert.assertEquals(user.getLastname(), retrievedUser.getLastname());
        Assert.assertEquals(user.getUsername(), retrievedUser.getUsername());
        Assert.assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testUpdateDoctorRoom() {
        Room room = roomService.createRoom("Sala 1");
        Room newRoom = roomService.createRoom("Sala 2");
        Doctor doctor = (Doctor) userService.createUser("John", "Doe", "dowjoe", "password", UserType.DOCTOR, room.getId());

        Doctor updatedDoctor = userService.updateDoctorRoom(doctor.getId(), newRoom.getId());

        Assert.assertNotNull(updatedDoctor);
        Assert.assertEquals(newRoom.getId(), updatedDoctor.getRoom().getId());
    }

    @Test
    public void testUpdateUserAccess() {
        User user =  userService.createUser("John", "Doe", "spy", "password", UserType.SECRETARY);
        String newPassword = "newPassword";

        User updatedUser = userService.updateUserAccess(user.getId(), user.getUsername(), user.getPassword(), newPassword);

        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(newPassword, updatedUser.getPassword());
    }


}