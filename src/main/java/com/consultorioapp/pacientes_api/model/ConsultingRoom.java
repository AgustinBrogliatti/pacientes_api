package com.consultorioapp.pacientes_api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultingRoom {
    private String name;
    private WaitingLine waitingLine;
}