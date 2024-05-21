package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Room;

import java.util.List;

public interface IRoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    boolean deleteRoomById(Long roomId);
}
