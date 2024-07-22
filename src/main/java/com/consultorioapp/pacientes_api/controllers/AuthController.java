package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.Dto.LoginDto;
import com.consultorioapp.pacientes_api.Dto.ResponseLoginDto;
import com.consultorioapp.pacientes_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.consultorioapp.pacientes_api.configuration.JwtUtilities;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtilities jwtUtilities;

    @PostMapping(path = "/login")
    public ResponseLoginDto authenticate(@RequestBody LoginDto loginDto) {
        return new ResponseLoginDto(userService.authenticate(loginDto.getUsername(), loginDto.getPassword()));
    }

    @PostMapping(path = "/validate-token")
    public boolean validateToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        return jwtUtilities.isValidToken(token);
    }


}

