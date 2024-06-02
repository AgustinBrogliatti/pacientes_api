package com.consultorioapp.pacientes_api.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordPreviewDto {
    private Long id;
    private Long patientDni;
    private String patientLastname;
    private String patientName;
    private String healthInsurances;
    private String patientPhoneNumber;
    private String doctorName;
}
