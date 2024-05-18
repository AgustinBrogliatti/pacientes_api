package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Room;

import java.util.List;

public interface IRoomService {
    Room createRoom(String roomName);
    List<Room> getAllRooms();
    boolean deleteRoomById(Long roomId);
}
