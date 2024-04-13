package com.consultorioapp.pacientes_api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Calendar {
    private List<Appointment> appointments;

    public void createAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
    }
}
