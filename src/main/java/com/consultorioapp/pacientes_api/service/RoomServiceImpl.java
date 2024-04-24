package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements IRoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room createRoom(String roomName) {
        Room newRoom = new Room(roomName);
        return roomRepository.save(newRoom);
    }

    @Override
    public Room getRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional.orElse(null);
    }
}
