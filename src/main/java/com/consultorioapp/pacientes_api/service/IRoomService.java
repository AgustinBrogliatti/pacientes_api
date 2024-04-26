package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Room;

import java.util.List;

public interface IRoomService {
    Room createRoom(String roomName);

    Room getRoomById(Long roomId);

    List<Room> getAllRooms();
}
