package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    Doctor createDoctor(String name, String lastname, String username, String password, Long roomId);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);
    Secretary createSecretary(String name, String lastname, String username, String password);

    User updateUserAccess(String username, String password);


}
