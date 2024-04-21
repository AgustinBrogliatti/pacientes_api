package com.consultorioapp.pacientes_api.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "dni", nullable = false)
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule", nullable = false)
    private Date schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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

