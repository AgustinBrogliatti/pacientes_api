package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.model.User;
import com.consultorioapp.pacientes_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsuario = this.userRepository.findByUsername(username);
        if(optionalUsuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        } else {
            User usuario = optionalUsuario.get();
            return usuario;
        }
    }
}
