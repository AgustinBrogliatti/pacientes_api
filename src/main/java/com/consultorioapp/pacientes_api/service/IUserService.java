package com.consultorioapp.pacientes_api.service;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;


public interface IUserService {
    Doctor createDoctor(String name, String lastname, String username, String password, Long roomId);
    Secretary createSecretary(String name, String lastname, String username, String password);
    Doctor getDoctorById(Long userId);
    Doctor updateDoctorRoom(Long doctorId, Long roomId);

}
