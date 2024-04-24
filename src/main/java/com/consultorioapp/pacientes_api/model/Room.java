package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "room")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    @OneToMany
    @JoinTable(
            name = "waiting_list",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_dni"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "patient_dni"}))
    @OrderColumn(name = "priority")
    private List<Patient> patients;

    public Room(String roomName) {
        this.name = roomName;
    }

    public void assignPatient(Patient patient) {
        this.patients.add(patient);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public void reorderPatients(List<Patient> patientsOrder) {
        this.patients.clear();
        this.patients.addAll(patientsOrder);
    }

    public void empty() {
        this.patients.clear();
    }
}