package com.consultorioapp.pacientes_api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Calendar {
    private List<Appointment> appointments;

    public List<Appointment> getDateByPatient(String lastName) {
        List<Appointment> foundAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getLastname().equalsIgnoreCase(lastName)) {
                foundAppointments.add(appointment);
            }
        }
        return foundAppointments;
    }

    public void createAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointment.getStatus() == AppointmentStatus.PENDING) {
            this.appointments.remove(appointment);
        } else {
            throw new IllegalStateException("Cannot cancel appointment that is not pending.");
        }
    }
}
