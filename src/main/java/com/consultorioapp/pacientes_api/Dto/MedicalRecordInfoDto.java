package com.consultorioapp.pacientes_api.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordInfoDto {
    private Long id;
    private String doctorId;
    private String healthInsurances;
    private String previousHistory;
    private String allergies;
    private String medicines;
}
