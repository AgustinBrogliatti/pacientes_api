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
        roomService.createRoom("UserRoom");
        Secretary newUser = (Secretary) userService.createUser("Secretaria", "Ficticia", "user-secretaria", "password", UserType.SECRETARY);

        Secretary savedSecretary = (Secretary) userRepository.findById(newUser.getId()).orElse(null);
        Assert.assertNotNull(savedSecretary);
        Assert.assertEquals("Secretaria", savedSecretary.getName());
        Assert.assertEquals("Ficticia", savedSecretary.getLastname());
        Assert.assertEquals("user-secretaria", savedSecretary.getUsername());
        Assert.assertEquals("password", savedSecretary.getPassword());
    }

    @Test
    public void testCreateUserDoctor() {
        Doctor newDoctor = (Doctor) userService.createUser("Javier", "Moreno", "user-doc", "password", UserType.DOCTOR, roomId);

        Doctor savedDoctor = (Doctor) userRepository.findById(newDoctor.getId()).orElse(null);
        Assert.assertNotNull(savedDoctor);
        Assert.assertEquals("Javier", savedDoctor.getName());
        Assert.assertEquals("Moreno", savedDoctor.getLastname());
        Assert.assertEquals("user-doc", savedDoctor.getUsername());
        Assert.assertEquals("password", savedDoctor.getPassword());
        Assert.assertEquals(roomId, savedDoctor.getRoom().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithExistingUsername() {
        userService.createUser("John", "Doe", "user-exist", "password", UserType.DOCTOR, roomId);
        try {
            userService.createUser("Jane", "Doe", "user-exist", "password", UserType.SECRETARY);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El nombre de usuario ya está en uso", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDoctorWithInvalidRoomId() {
        try {
            userService.createUser("Juan", "Pérez", "user-room-exist", "password", UserType.DOCTOR, 555L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El roomId proporcionado no es válido", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithInvalidUserType() {
        try {
            userService.createUser("Jane", "Doe", "user-invalidType", "password", "InvalidType");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Tipo de usuario no válido", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetDoctorById() {
        User user =  userService.createUser("John", "Doe", "get-user", "password", UserType.SECRETARY);
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
        Doctor doctor = (Doctor) userService.createUser("John", "Doe", "user-update-room", "password", UserType.DOCTOR, room.getId());

        Doctor updatedDoctor = userService.updateDoctorRoom(doctor.getId(), newRoom.getId());

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
        Doctor doctor = (Doctor) userService.createUser("John", "Doe", "user-invalid-room", "password", UserType.DOCTOR, 1L);
        try {
            userService.updateDoctorRoom(doctor.getId(), 999L);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Habitación con id 999 no encontrada", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testUpdateUserAccess() {
        User user =  userService.createUser("John", "Doe", "user-update-login", "password", UserType.SECRETARY);
        String newPassword = "newPassword";

        User updatedUser = userService.updateUserAccess(user.getUsername(), user.getPassword(), newPassword);

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

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserAccessWithIncorrectCredentials() {
        User user = userService.createUser("John", "Doe", "invalid-pass-update", "password", UserType.SECRETARY);
        try {
            userService.updateUserAccess("invalid-pass-update", "incorrectPassword", "newPassword");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Nombre de usuario o contraseña incorrectos", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testDeleteUser() {
        User user = userService.createUser("John", "Doe", "user-to-delete", "password", UserType.DOCTOR, 1L);
        User deletedUser = userService.deleteUser(user.getUsername(), user.getPassword());

        Assert.assertNotNull(deletedUser);
        Assert.assertEquals(user.getId(), deletedUser.getId());
        Assert.assertEquals(user.getUsername(), deletedUser.getUsername());
        Assert.assertEquals(user.getName(), deletedUser.getName());
        Assert.assertEquals(user.getLastname(), deletedUser.getLastname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithInvalidUsername() {
        try {
            userService.deleteUser("invalid-username", "password");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Usuario no encontrado. Username: invalid-username", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithInvalidPassword() {
        User user = userService.createUser("John", "Doe", "user-to-delete", "password", UserType.DOCTOR, 1L);

        try {
            userService.deleteUser(user.getUsername(), "invalid-password");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Nombre de usuario o contraseña incorrectos", e.getMessage());
            throw e;
        }
    }


}