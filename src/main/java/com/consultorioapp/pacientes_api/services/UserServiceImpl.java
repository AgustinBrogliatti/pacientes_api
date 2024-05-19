package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Doctor createDoctor(Doctor doctor, Long roomId) {
        if (userRepository.findByUsername(doctor.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("La sala con ID " + roomId + " no existe"));
        doctor.setRoom(room);
        return userRepository.save(doctor);
    }

    @Override
    @Transactional
    public Secretary createSecretary(Secretary secretary) {
        if (userRepository.findByUsername(secretary.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        return userRepository.save(secretary);
    }

    @Override
    @Transactional
    public Doctor updateDoctorRoom(Long doctorId, Long newRoomId) {
        Optional<User> doctorOptional = userRepository.findById(doctorId);
        Optional<Room> roomOptional = roomRepository.findById(newRoomId);

        if (doctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor con id " + doctorId + " no encontrado");
        }
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Habitación con id " + newRoomId + " no encontrada");
        }
        if (Objects.equals(doctorOptional.get().getRoom().getId(), newRoomId)) {
            throw new IllegalArgumentException("la roomId: " + newRoomId + " ya se encuentra asignada a este usuario id: " + doctorId);
        }

        Doctor doctor = (Doctor) doctorOptional.get();
        Room room = roomOptional.get();
        doctor.setRoom(room);
        return userRepository.save(doctor);
    }

    @Override
    @Transactional
    public User updateUserAccess(String username, String password, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Nombre de usuario o contraseña incorrectos");
        }

        User user = userOptional.get();

        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Nombre de usuario o contraseña incorrectos");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado. ID: " + userId);
        }

        User user = userOptional.get();

        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
