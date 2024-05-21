package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    Doctor createDoctor(Doctor doctor, Long roomId);
    Secretary createSecretary(Secretary secretary);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);
    boolean updateUserAccess(String username, String password, String newPassword);
    boolean deleteUser(Long userId);

}
