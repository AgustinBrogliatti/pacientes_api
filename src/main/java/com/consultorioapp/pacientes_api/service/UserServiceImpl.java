package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.repository.RoomRepository;
import com.consultorioapp.pacientes_api.repository.UserRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
