package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(String name, String lastname, String username, String password, String userType) {
        return createUser(name, lastname, username, password, userType, null);
    }

    @Override
    @Transactional
    public User createUser(String name, String lastname, String username, String password, String userType, Long roomId) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        if (userType.equals(UserType.DOCTOR)) {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new IllegalArgumentException("El roomId proporcionado no es válido"));
            Doctor newDoctor = new Doctor(name, lastname, username, password, room);
            return userRepository.save(newDoctor);
        } else if (userType.equals(UserType.SECRETARY)) {
            Secretary newSecretary = new Secretary(name, lastname, username, password);
            return userRepository.save(newSecretary);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
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
    public User deleteUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado. Username: " + username);
        }

        User user = userOptional.get();

        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Nombre de usuario o contraseña incorrectos");
        }

        userRepository.delete(user);
        return user;
    }

}
