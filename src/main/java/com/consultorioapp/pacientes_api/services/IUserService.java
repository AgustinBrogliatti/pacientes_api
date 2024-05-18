package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    Doctor createDoctor(Doctor doctor);
    Secretary createSecretary(Secretary secretary);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);
    User updateUserAccess(String username, String password, String newPassword);
    boolean deleteUser(User user);

}
