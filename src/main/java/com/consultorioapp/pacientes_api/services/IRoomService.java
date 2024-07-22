package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.model.Room;

import java.util.List;

public interface IRoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    boolean deleteRoomById(Long roomId);
    Room addPatientToRoom(Long patientId, Long roomId);
    Room removePatientFromRoom(Long patientId, Long roomId);
    Room reorderPatients(Long roomId, List<Long> patientIdsInOrder);
    Room emptyRoom(Long roomId);
    List<Patient> getPatientsInRoom(Long roomId);
}
