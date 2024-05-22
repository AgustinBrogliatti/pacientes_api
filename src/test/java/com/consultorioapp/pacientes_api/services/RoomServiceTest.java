package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
public class RoomServiceTest {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testCreateRoom() {
        String roomName = "Sarmiento";
        Room createdRoom = roomService.createRoom(new Room(roomName));
        Assert.assertNotNull(createdRoom);
        Assert.assertEquals(roomName, createdRoom.getName());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateRoomWithExistingName() {
        String roomName = "Existing Room";
        roomService.createRoom(new Room(roomName));

        try {
            roomService.createRoom(new Room(roomName));
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testGetAllRooms() {
        roomService.createRoom(new Room("Room 1"));
        roomService.createRoom(new Room("Room 2"));
        roomService.createRoom(new Room("Room 3"));

        List<Room> rooms = roomService.getAllRooms();
        Assert.assertNotNull(rooms);
        Assert.assertEquals(3, rooms.size());
        Assert.assertEquals("Room 1", rooms.get(0).getName());
        Assert.assertEquals("Room 2", rooms.get(1).getName());
        Assert.assertEquals("Room 3", rooms.get(2).getName());
    }

    @Test
    public void testDeleteRoomById() {
        Room room = roomService.createRoom(new Room("Sala Test"));
        boolean isDeleted = roomService.deleteRoomById(room.getId());
        Assert.assertTrue(isDeleted);
    }

    @Test(expected = Exception.class)
    public void testDeleteRoomByIdWithInvalidId() {
        try {
            roomService.deleteRoomById(999L);
        } catch (Exception e) {
            throw e;
        }
    }
}
