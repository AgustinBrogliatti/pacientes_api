package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements IRoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public Room createRoom(String roomName) {
        try {
            Room newRoom = new Room(roomName);
            return roomRepository.save(newRoom);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El nombre de la sala ya está en uso");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Room getRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(rooms::add);
        return rooms;
    }

    public Room deleteRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Sala no encontrada. ID: " + roomId);
        }

        Room room = roomOptional.get();
        roomRepository.delete(room);
        return room;
    }

}
