package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue(UserType.DOCTOR)

@NoArgsConstructor
@Getter
@Setter
public class Doctor extends User {
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public Doctor(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }

    public String getFullName() {
        return this.getName() + " " + this.getLastname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("DOC"));
        return authorities;
    }
}