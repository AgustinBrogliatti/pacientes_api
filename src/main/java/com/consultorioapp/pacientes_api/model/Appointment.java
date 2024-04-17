package com.consultorioapp.pacientes_api.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName="user_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName="patient_id")
    private Patient patient;

    private Date schedule;
    private String status;

    public Appointment(Date schedule, Doctor doctor, Patient patient) {
        this.schedule = schedule;
        this.doctor = doctor;
        this.patient = patient;
        this.status = "Pendiente";
    }

    public void rescheduleAppointment() {
        // Implementación para reprogramar la cita
    }
}
