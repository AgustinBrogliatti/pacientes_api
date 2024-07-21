package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.DoctorDto;
import com.consultorioapp.pacientes_api.Dto.SecretaryDto;
import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;


public interface IUserService {
    DoctorDto createDoctor(Doctor doctor, Long roomId);
    SecretaryDto createSecretary(Secretary secretary);
    DoctorDto updateDoctorRoom(Long doctorId, Long roomId);
    boolean updateUserAccess(String username, String password, String newPassword);
    boolean deleteUser(Long userId);
    String authenticate(String username, String password);
    User getUserInfo();
}
