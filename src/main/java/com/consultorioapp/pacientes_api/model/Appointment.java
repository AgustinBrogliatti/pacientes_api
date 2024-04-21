package com.consultorioapp.pacientes_api.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointments")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule")
    private Date schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

    public Appointment(Date schedule, Doctor doctor, Patient patient) {
        this.schedule = schedule;
        this.doctor = doctor;
        this.patient = patient;
        this.status = AppointmentStatus.PENDING;
    }

    public void rescheduleAppointment() {
        // Implementación para reprogramar la cita
    }

    public enum AppointmentStatus {
        PENDING,
        COMPLETED,
        CANCELLED,
    }
}

