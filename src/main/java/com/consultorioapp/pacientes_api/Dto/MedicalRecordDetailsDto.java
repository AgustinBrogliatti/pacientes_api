package com.consultorioapp.pacientes_api.Dto;

import com.consultorioapp.pacientes_api.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDetailsDto {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private Patient patient;
    private String healthInsurances;
    private String allergies;
    private String medicines;
    private String previousHistory;
}
