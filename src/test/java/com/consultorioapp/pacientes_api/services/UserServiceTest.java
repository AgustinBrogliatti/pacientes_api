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
        roomService.createRoom(new Room("userRoom"));
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
        Room newRoom = roomService.createRoom(new Room("room-2"));
        Doctor newDoctor = new Doctor("Javier", "Moreno", "user-doc", "password");
        Doctor createdUser = userService.createDoctor(newDoctor, newRoom.getId());

        Doctor savedDoctor = (Doctor) userRepository.findById(newDoctor.getId()).orElse(null);

        Assert.assertNotNull(savedDoctor);
        Assert.assertEquals(createdUser.getName(), savedDoctor.getName());
        Assert.assertEquals(createdUser.getLastname(), savedDoctor.getLastname());
        Assert.assertEquals(createdUser.getUsername(), savedDoctor.getUsername());
        Assert.assertEquals(createdUser.getPassword(), savedDoctor.getPassword());
        Assert.assertEquals(newRoom.getId(), savedDoctor.getRoom().getId());
    }

    @Test(expected = Exception.class)
    public void testCreateUserWithExistingUsername() {
        Room newRoom = roomService.createRoom(new Room("room-3"));
        Secretary newUser = new Secretary("John", "Doe", "user-exist", "password");
        userService.createSecretary(newUser);

        try {
            userService.createSecretary(newUser);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testCreateDoctorWithInvalidRoomId() {
        Room room = new Room("bad-room");
        Doctor newDoctor = new Doctor("Javier", "Moreno", "user-bad-room", "password");

        try {
            userService.createDoctor(newDoctor, room.getId());
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testUpdateDoctorRoom() {
        Room room = roomService.createRoom(new Room("Sala 1"));
        Room newRoom = roomService.createRoom(new Room("Sala 2"));

        Doctor newDoctor = new Doctor("John", "Doe", "user-update-room", "password");
        Doctor createdUser = userService.createDoctor(newDoctor, room.getId());

        Doctor updatedDoctor = userService.updateDoctorRoom(newDoctor.getId(), newRoom.getId());

        Assert.assertNotNull(updatedDoctor);
        Assert.assertEquals(newRoom.getId(), updatedDoctor.getRoom().getId());
    }

    @Test(expected = Exception.class)
    public void testUpdateDoctorRoomWithInvalidDoctorId() {
        try {
            userService.updateDoctorRoom(999L, 1L);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(expected = Exception.class)
    public void testUpdateDoctorRoomWithInvalidRoomId() {
        Room room = roomService.createRoom(new Room("Sala 10"));
        Doctor newDoctor = new Doctor("John", "Doe", "user-invalid-room", "password");
        Doctor createdUser = userService.createDoctor(newDoctor, room.getId());
        try {
            userService.updateDoctorRoom(newDoctor.getId(), 999L);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testUpdateUserAccess() {
        Secretary newUser = new Secretary("John", "Doe", "user-update-login", "password");
        Secretary createdUser = userService.createSecretary(newUser);
        String newPassword = "newPassword";

        boolean isUpdated = userService.updateUserAccess(createdUser.getUsername(), createdUser.getPassword(), newPassword);
        Assert.assertTrue(isUpdated);
    }

    @Test(expected = Exception.class)
    public void testUpdateUserAccessWithInvalidUserId() {
        try {
            userService.updateUserAccess("invalid-user", "password", "newPassword");
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testDeleteUser() {
        Secretary newUser = new Secretary("John", "Doe", "user-to-delete", "password");
        Secretary createdUser = userService.createSecretary(newUser);

        boolean isDeleted = userService.deleteUser(newUser.getId());
        Assert.assertTrue(isDeleted);
    }

    @Test(expected = Exception.class)
    public void testDeleteUserWithInvalidUsername() {
        Secretary newUser = new Secretary("John", "Doe", "false-user", "password");

        try {
            userService.deleteUser(newUser.getId());
        } catch (Exception e) {
            throw e;
        }
    }

}