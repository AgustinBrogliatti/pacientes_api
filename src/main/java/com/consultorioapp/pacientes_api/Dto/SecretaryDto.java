package com.consultorioapp.pacientes_api.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretaryDto {
    private Long id;
    private String name;
    private String lastname;
}
