package com.consultorioapp.pacientes_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@DiscriminatorValue(UserType.SECRETARY)
@NoArgsConstructor
@Getter
@Setter
public class Secretary extends User {
    public Secretary(String name, String lastname, String username, String password) {
        super(name, lastname, username, password);
    }

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    public void createAppointment(Calendar calendar, Doctor doctor, Patient patient, Date date) {
        Appointment newAppointment = new Appointment(date, doctor, patient);
        calendar.createAppointment(newAppointment);
    }

    public void cancelAppointment(Calendar calendar, Appointment appointment) {
        appointment.cancelAppointment();
        calendar.cancelAppointment(appointment);
    }

    public void rescheduleAppointment(Appointment appointment, Date new_date) {
        appointment.reschedule(new_date);
    }

    public void updateAppointmentStatus(Appointment appointment, AppointmentStatus newStatus) {
        EnumSet<AppointmentStatus> validStatus = EnumSet.of(AppointmentStatus.PENDING, AppointmentStatus.COMPLETED, AppointmentStatus.CANCELLED);
        if (!validStatus.contains(newStatus)) {
            throw new IllegalArgumentException("Invalid appointment status");
        }
        if(newStatus != appointment.getStatus()) {
            appointment.setStatus(newStatus);
        }
    }

    public MedicalRecord newMedicalRecord(Patient patient, Doctor doctor, String healthInsurances, String allergies, String medicines, String previousHistory) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setHealthInsurances(healthInsurances);
        medicalRecord.setAllergies(allergies);
        medicalRecord.setMedicines(medicines);
        medicalRecord.setPreviousHistory(previousHistory);
        return medicalRecord;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
}
