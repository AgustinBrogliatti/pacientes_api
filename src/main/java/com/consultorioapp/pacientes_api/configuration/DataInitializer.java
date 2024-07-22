package com.consultorioapp.pacientes_api.configuration;

import com.consultorioapp.pacientes_api.model.God;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //inyectamos el valor desde app.properties
    @Value("${superuser.password}")
    private String superuserPassword;

    @Override
    public void run(String... args) throws Exception {
//        if (!userRepository.existsByUsername("superuser")) {
//            God superuser = new God();
//            superuser.setName("Super");
//            superuser.setLastname("User");
//            superuser.setUsername("agus-dev");
//            superuser.setPassword(passwordEncoder.encode(superuserPassword));
//
//            userRepository.save(superuser);
//        }
    }
}