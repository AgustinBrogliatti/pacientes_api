package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        Room createdRoom = roomService.createRoom(roomName);
        Assert.assertNotNull(createdRoom);
        Assert.assertEquals(roomName, createdRoom.getName());
//        roomRepository.deleteById(createdRoom.getId());
    }

    @Test
    public void testCreateRoomWithExistingName() {
        String roomName = "Existing Room";
        roomService.createRoom(roomName);

        try {
            roomService.createRoom(roomName);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("El nombre de la sala ya está en uso", e.getMessage());
            return;
        }

        Assert.fail("Se esperaba una excepción al intentar crear una sala con un nombre existente.");
    }

    @Test
    public void testGetRoomById() {
        String roomName = "Rivadavia";
        Room createdRoom = roomService.createRoom(roomName);
        Room retrievedRoom = roomService.getRoomById(createdRoom.getId());
        Assert.assertNotNull(retrievedRoom);
        Assert.assertEquals(roomName, retrievedRoom.getName());
//        roomRepository.deleteById(createdRoom.getId());
    }

    @Test
    public void testGetAllRooms() {
        roomService.createRoom("Room 1");
        roomService.createRoom("Room 2");
        roomService.createRoom("Room 3");

        List<Room> rooms = roomService.getAllRooms();
        Assert.assertNotNull(rooms);
        Assert.assertEquals(3, rooms.size());
        Assert.assertEquals("Room 1", rooms.get(0).getName());
        Assert.assertEquals("Room 2", rooms.get(1).getName());
        Assert.assertEquals("Room 3", rooms.get(2).getName());
//        roomRepository.deleteAll();
    }
}
