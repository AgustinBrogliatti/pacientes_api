package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    User createUser(String name, String lastname, String username, String password, String userType, Long roomId);
    User createUser(String name, String lastname, String username, String password, String userType);
    User getUserById(Long userId);
    User updateUserAccess(String username, String password, String newPassword);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);

}
