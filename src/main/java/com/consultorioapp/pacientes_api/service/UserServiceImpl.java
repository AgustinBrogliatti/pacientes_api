package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import com.consultorioapp.pacientes_api.repository.UserRepository;
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
    public Doctor createDoctor(String name, String lastname, String username, String password, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("El roomId proporcionado no es válido"));

        Doctor newDoctor = new Doctor(name, lastname, username, password, room);
        return userRepository.save(newDoctor);
    }

    @Override
    @Transactional
    public Secretary createSecretary(String name, String lastname, String username, String password) {
        Secretary newSecretary = new Secretary(name, lastname, username, password);
        return userRepository.save(newSecretary);
    }

    @Override
    @Transactional
    public Doctor getDoctorById(Long doctorId) {
        Optional<User> doctorOptional = userRepository.findById(doctorId);
        return (Doctor) doctorOptional.orElse(null);
    }

    @Override
    @Transactional
    public Doctor updateDoctorRoom(Long doctorId, Long roomId) {
        Optional<User> doctorOptional = userRepository.findById(doctorId);
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (doctorOptional.isPresent() && roomOptional.isPresent()) {
            Doctor doctor = (Doctor) doctorOptional.get();
            Room room = roomOptional.get();
            doctor.setRoom(room);
            return userRepository.save(doctor);
        }
        return null;
    }

    @Override
    @Transactional
    public User updateUserAccess(Long userId, String username, String password, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.setPassword(newPassword);
                return userRepository.save(user);
            }
        }
        return null;
    }
}
