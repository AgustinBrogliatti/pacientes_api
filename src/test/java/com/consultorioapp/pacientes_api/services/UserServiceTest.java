package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

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
    @Test
    public void testCreateUserSecretary() {
        roomService.createRoom("UserRoom");
        Secretary newUser = new Secretary("Secretaria", "Ficticia", "user-secretaria", "password");
        Secretary createdUser = userService.createSecretary(newUser);

        Secretary savedSecretary = (Secretary) userRepository.findById(newUser.getId()).orElse(null);

        Assert.assertNotNull(savedSecretary);
        Assert.assertEquals(createdUser.getName(), savedSecretary.getName());
        Assert.assertEquals(createdUser.getLastname(), savedSecretary.getLastname());
        Assert.assertEquals(createdUser.getUsername(), savedSecretary.getUsername());
        Assert.assertEquals(createdUser.getPassword(), savedSecretary.getPassword());
    }

    @Test
    public void testCreateUserDoctor() {
        Room newRoom = roomService.createRoom("room-2");
        Doctor newDoctor = new Doctor("Javier", "Moreno", "user-doc", "password", newRoom);
        Doctor createdUser = userService.createDoctor(newDoctor);

        Doctor savedDoctor = (Doctor) userRepository.findById(newDoctor.getId()).orElse(null);

        Assert.assertNotNull(savedDoctor);
        Assert.assertEquals(createdUser.getName(), savedDoctor.getName());
        Assert.assertEquals(createdUser.getLastname(), savedDoctor.getLastname());
        Assert.assertEquals(createdUser.getUsername(), savedDoctor.getUsername());
        Assert.assertEquals(createdUser.getPassword(), savedDoctor.getPassword());
        Assert.assertEquals(newRoom.getId(), savedDoctor.getRoom().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithExistingUsername() {
        Room newRoom = roomService.createRoom("room-3");
        Secretary newUser = new Secretary("John", "Doe", "user-exist", "password");
        userService.createSecretary(newUser);

        try {
            userService.createSecretary(newUser);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El nombre de usuario ya está en uso", e.getMessage());
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDoctorWithInvalidRoomId() {
        Room room = new Room("bad-room");
        Doctor newDoctor = new Doctor("Javier", "Moreno", "user-bad-room", "password", room);

        try {
            userService.createDoctor(newDoctor);
        } catch (RuntimeException e) {
            String expectedMessage = "El roomId proporcionado no es válido";
            throw e;
        }
    }

    @Test
    public void testUpdateDoctorRoom() {
        Room room = roomService.createRoom("Sala 1");
        Room newRoom = roomService.createRoom("Sala 2");

        Doctor newDoctor = new Doctor("John", "Doe", "user-update-room", "password", room);
        Doctor createdUser = userService.createDoctor(newDoctor);

        Doctor updatedDoctor = userService.updateDoctorRoom(newDoctor.getId(), newRoom.getId());

        Assert.assertNotNull(updatedDoctor);
        Assert.assertEquals(newRoom.getId(), updatedDoctor.getRoom().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateDoctorRoomWithInvalidDoctorId() {
        try {
            userService.updateDoctorRoom(999L, 1L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Doctor con id 999 no encontrado", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateDoctorRoomWithInvalidRoomId() {
        Room room = roomService.createRoom("Sala 10");
        Doctor newDoctor = new Doctor("John", "Doe", "user-invalid-room", "password", room);
        Doctor createdUser = userService.createDoctor(newDoctor);
        try {
            userService.updateDoctorRoom(newDoctor.getId(), 999L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Habitación con id 999 no encontrada", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testUpdateUserAccess() {
        Secretary newUser = new Secretary("John", "Doe", "user-update-login", "password");
        Secretary createdUser = userService.createSecretary(newUser);

        String newPassword = "newPassword";

        User updatedUser = userService.updateUserAccess(createdUser.getUsername(), createdUser.getPassword(), newPassword);

        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserAccessWithInvalidUserId() {
        try {
            userService.updateUserAccess("invalid-user", "password", "newPassword");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Usuario no encontrado. Username: invalid-user", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testDeleteUser() {
        Secretary newUser = new Secretary("John", "Doe", "user-to-delete", "password");
        Secretary createdUser = userService.createSecretary(newUser);

        boolean isDeleted = userService.deleteUser(createdUser);
        Assert.assertTrue(isDeleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithInvalidUsername() {
        Secretary newUser = new Secretary("John", "Doe", "false-user", "password");

        try {
            userService.deleteUser(newUser);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Usuario no encontrado. Username: false-user", e.getMessage());
            throw e;
        }
    }

}