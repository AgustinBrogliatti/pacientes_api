package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        if (userRepository.findByUsername(doctor.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Room room = roomRepository.findById(doctor.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("El roomId proporcionado no es válido"));
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
            throw new IllegalArgumentException("Usuario no encontrado. Username: " + username);
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
    public boolean deleteUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado. Username: " + user.getUsername());
        }

        User userReceived = userOptional.get();

        if (!user.getUsername().equals(userReceived.getUsername()) || !user.getPassword().equals(userReceived.getPassword())) {
            throw new IllegalArgumentException("Nombre de usuario o contraseña incorrectos");
        }

        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
