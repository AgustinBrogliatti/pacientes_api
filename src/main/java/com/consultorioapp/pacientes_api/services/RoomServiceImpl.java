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
    public Room createRoom(Room room) {
        try {
            return roomRepository.save(room);
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRooms(){
        try {
            List<Room> rooms = new ArrayList<>();
            roomRepository.findAll().forEach(rooms::add);
            return rooms;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteRoomById(Long roomId) {
        try {
            Optional<Room> roomOptional = roomRepository.findById(roomId);
            Room room = roomOptional.get();
            roomRepository.delete(room);
            return true;
        } catch (Exception e) {
            throw  e;
        }
    }

}
