package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@DiscriminatorValue("Admin")

@NoArgsConstructor
@Getter
@Setter
public class Secretary extends User {

    public Secretary(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }

    public void createAppointment(Doctor doctor, Patient patient, Date date) {
        // Implementación para crear una cita
    }

    public void cancel_appointment(Appointment appointment) {
        // Implementación para cancelar una cita
    }

    public void reschedule_appointment(Appointment appointment, Date new_date) {
        // Implementación para reprogramar una cita
    }

    public void view_waiting_patients(Room room) {
        // Implementación específica para los secretarios
    }
}
