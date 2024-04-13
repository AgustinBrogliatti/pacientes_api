package com.consultorioapp.pacientes_api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    public void view_waiting_patients(ConsultingRoom room) {
        // Implementación específica para los secretarios
    }
}
