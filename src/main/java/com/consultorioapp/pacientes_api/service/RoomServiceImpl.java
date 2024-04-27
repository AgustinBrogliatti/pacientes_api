package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
    @Transactional
    public Room getRoomById(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        return roomOptional.orElse(null);
    }

    @Override
    @Transactional
    public List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(rooms::add);
        return rooms;
    }

}
