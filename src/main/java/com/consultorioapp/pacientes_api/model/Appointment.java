package com.consultorioapp.pacientes_api.model;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Appointment {
    private Date schedule;
    private Doctor dr;
    private Patient patient;
    private String status;

    public Appointment(Date schedule, Doctor dr, Patient patient) {
        this.schedule = schedule;
        this.dr = dr;
        this.patient = patient;
        this.status = "Pendiente";
    }

    public void rescheduleAppointment() {
        // Implementación para reprogramar la cita
    }
}
