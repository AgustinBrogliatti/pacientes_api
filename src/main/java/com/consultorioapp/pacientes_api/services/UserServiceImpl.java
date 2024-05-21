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
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("La sala con ID " + roomId + " no existe"));
        doctor.setRoom(room);
        return userRepository.save(doctor);
    }

    @Override
    @Transactional
    public Secretary createSecretary(Secretary secretary) {
        return userRepository.save(secretary);
    }

    @Override
    @Transactional
    public Doctor updateDoctorRoom(Long doctorId, Long newRoomId) {
        try {
            Optional<User> doctorOptional = userRepository.findById(doctorId);
            Optional<Room> roomOptional = roomRepository.findById(newRoomId);

            Doctor doctor = (Doctor) doctorOptional.get();
            Room room = roomOptional.get();
            doctor.setRoom(room);
            return userRepository.save(doctor);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean updateUserAccess(String username, String password, String newPassword) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
            User user = userOptional.get();

            if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Nombre de usuario o contraseña incorrectos");
            }

            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            User user = userOptional.get();
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            throw  e;
        }
    }
}
