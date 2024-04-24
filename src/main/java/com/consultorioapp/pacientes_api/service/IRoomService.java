package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Room;

public interface IRoomService {
    Room createRoom(String roomName);

    Room getRoomById(Long roomId);
}
