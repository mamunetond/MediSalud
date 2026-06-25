package com.medisalud.citas_api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaRequest {

    @NotNull(message = "El paciente es obligatorio")
    private UUID pacienteId;

    @NotNull(message = "El médico es obligatorio")
    private UUID medicoId;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;
}