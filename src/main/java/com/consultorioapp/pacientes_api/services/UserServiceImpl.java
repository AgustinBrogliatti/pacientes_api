package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.DoctorDto;
import com.consultorioapp.pacientes_api.Dto.SecretaryDto;
import com.consultorioapp.pacientes_api.configuration.JwtUtilities;
import com.consultorioapp.pacientes_api.model.*;
import com.consultorioapp.pacientes_api.repositories.RoomRepository;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilities jwtUtilities;


    private DoctorDto convertToDoctorDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setLastname(doctor.getLastname());
        doctorDto.setRoomId(doctor.getRoom().getId());
        doctorDto.setRoomName(doctor.getRoom().getName());
        return doctorDto;
    }

    private SecretaryDto convertotoSecDto(Secretary secretary) {
        SecretaryDto secretaryDto = new SecretaryDto();
        secretaryDto.setId(secretary.getId());
        secretaryDto.setName(secretary.getName());
        secretaryDto.setLastname(secretary.getLastname());
        return secretaryDto;
    }

    @Override
    @Transactional
    public DoctorDto createDoctor(Doctor doctor, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("La sala con ID " + roomId + " no existe"));
        doctor.setRoom(room);
        return convertToDoctorDto(userRepository.save(doctor));
    }

    @Override
    @Transactional
    public SecretaryDto createSecretary(Secretary secretary) {
        return convertotoSecDto(userRepository.save(secretary));
    }

    @Override
    @Transactional
    public DoctorDto updateDoctorRoom(Long doctorId, Long newRoomId) {
        try {
            Optional<User> doctorOptional = userRepository.findById(doctorId);
            Optional<Room> roomOptional = roomRepository.findById(newRoomId);

            Doctor doctor = (Doctor) doctorOptional.get();
            Room room = roomOptional.get();
            doctor.setRoom(room);
            return convertToDoctorDto(userRepository.save(doctor));
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

    @Override
    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) { return null; }
        // Generar el token a retornar
        String token = jwtUtilities.generateToken(user.getUsername(), user.getId(), user.getRole());
        return token;

        // ACLARACION: Solo estoy retornando el JWT, el usuario no esta actualmente autenticado
        // por lo que si voy a realizar otra tarea debo generar el objeto correspondiente y
        // agregarlo al contexto de Spring

    }

    @Override
    public User getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElse(null);
    }
}
