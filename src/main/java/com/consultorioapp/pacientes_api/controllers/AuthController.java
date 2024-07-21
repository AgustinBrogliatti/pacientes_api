package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.Dto.LoginDto;
import com.consultorioapp.pacientes_api.Dto.ResponseLoginDto;
import com.consultorioapp.pacientes_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping(path = "/login")
    public ResponseLoginDto authenticate(@RequestBody LoginDto loginDto) {
        return new ResponseLoginDto(userService.authenticate(loginDto.getUsername(), loginDto.getPassword()));
    }

}

