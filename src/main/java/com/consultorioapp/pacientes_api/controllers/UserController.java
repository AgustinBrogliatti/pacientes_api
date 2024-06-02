package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.Dto.DoctorDto;
import com.consultorioapp.pacientes_api.Dto.SecretaryDto;
import com.consultorioapp.pacientes_api.configuration.ErrorResponse;
import com.consultorioapp.pacientes_api.model.Doctor;
import com.consultorioapp.pacientes_api.model.Secretary;
import com.consultorioapp.pacientes_api.model.User;
import com.consultorioapp.pacientes_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private IUserService userService;

    // POST ../api/user/doctor
    @PostMapping(value = "/doctor")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor, @RequestParam Long roomId) {
        try {
            DoctorDto createdDoctor = userService.createDoctor(doctor, roomId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // POST ../api/user/secretary
    @PostMapping(value = "/secretary")
    public ResponseEntity<?> createSecretary(@RequestBody Secretary secretary) {
        try {
            SecretaryDto createSecretary = userService.createSecretary(secretary);
            return ResponseEntity.status(HttpStatus.CREATED).body(createSecretary);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // PUT ../api/user/doctor/{doctorId}/room/{roomId}
    @PutMapping(value = "/doctor/{doctorId}/room/{roomId}")
    public ResponseEntity<?> updateDoctorRoom(@PathVariable Long doctorId, @PathVariable Long roomId) {
        try {
            DoctorDto updatedDoctor = userService.updateDoctorRoom(doctorId, roomId);
            return ResponseEntity.ok(updatedDoctor);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // PUT ../api/user/access
    @PutMapping(value = "/access")
    public ResponseEntity<?> updateUserAccess(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        String newPassword = requestBody.get("newPassword");

        try {
            boolean updated = userService.updateUserAccess(username, password, newPassword);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // DELETE ../api/user
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            boolean deleted = userService.deleteUser(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("user_id", userId);
            response.put("message", "Deleted successfully");
            response.put("deleted", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}

