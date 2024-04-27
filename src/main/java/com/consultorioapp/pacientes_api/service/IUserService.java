package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    User createUser(String name, String lastname, String username, String password, String userType, Long roomId);
    User createUser(String name, String lastname, String username, String password, String userType);
    User getUserById(Long userId);
    User updateUserAccess(Long userId, String username, String password, String newPassword);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);

}
