package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;
import com.consultorioapp.pacientes_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private IUserService userService;

    // POST ../api/user/doctor
    @PostMapping(value = "/doctor")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return userService.createDoctor(doctor);
    }

    // POST ../api/user/secretary
    @PostMapping(value = "/secretary")
    public Secretary createSecretary(@RequestBody Secretary secretary) {
        return userService.createSecretary(secretary);
    }

    // PUT ../api/user/doctor/{doctorId}/room/{roomId}
    @PutMapping(value = "/doctor/{doctorId}/room/{roomId}")
    public Doctor updateDoctorRoom(@PathVariable Long doctorId, @PathVariable Long roomId) {
        return userService.updateDoctorRoom(doctorId, roomId);
    }

    // PUT ../api/user/access
    @PutMapping(value = "/access")
    public User updateUserAccess(@RequestParam String username, @RequestParam String password, @RequestParam String newPassword) {
        return userService.updateUserAccess(username, password, newPassword);
    }

    // DELETE ../api/user
    @DeleteMapping(value = "")
    public boolean deleteUser(@RequestBody User user) {
        return userService.deleteUser(user);
    }
}
