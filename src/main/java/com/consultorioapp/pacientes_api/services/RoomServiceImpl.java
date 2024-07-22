package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repositories.PatientRepository;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements IRoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PatientRepository patientRepository;

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

    @Override
    @Transactional
    public Room addPatientToRoom(Long patientId, Long roomId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Room> roomOpt = roomRepository.findById(roomId);

        if (patientOpt.isPresent() && roomOpt.isPresent()) {
            Patient patient = patientOpt.get();
            Room room = roomOpt.get();

            room.assignPatient(patient);
            return roomRepository.save(room);
        }
        throw new IllegalArgumentException("Patient or Room not found");
    }

    @Override
    @Transactional
    public Room removePatientFromRoom(Long patientId, Long roomId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Room> roomOpt = roomRepository.findById(roomId);

        if (patientOpt.isPresent() && roomOpt.isPresent()) {
            Patient patient = patientOpt.get();
            Room room = roomOpt.get();

            room.removePatient(patient);
            return roomRepository.save(room);
        }
        throw new IllegalArgumentException("Patient or Room not found");
    }

    @Override
    @Transactional
    public Room reorderPatients(Long roomId, List<Long> patientIdsInOrder) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);

        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            List<Patient> orderedPatients = new ArrayList<>();

            for (Long patientId : patientIdsInOrder) {
                Optional<Patient> patientOpt = patientRepository.findById(patientId);
                if (patientOpt.isPresent()) {
                    orderedPatients.add(patientOpt.get());
                } else {
                    throw new IllegalArgumentException("Patient with ID " + patientId + " not found");
                }
            }

            room.reorderPatients(orderedPatients);
            return roomRepository.save(room);
        }
        throw new IllegalArgumentException("Room not found");
    }

    @Override
    @Transactional
    public Room emptyRoom(Long roomId) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);

        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.empty();
            return roomRepository.save(room);
        }
        throw new IllegalArgumentException("Room not found");
    }

    @Override
    @Transactional
    public List<Patient> getPatientsInRoom(Long roomId) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            return room.getPatients();
        }
        throw new IllegalArgumentException("Room not found");
    }

}
