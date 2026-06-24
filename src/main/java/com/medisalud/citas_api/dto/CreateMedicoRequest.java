package com.medisalud.citas_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMedicoRequest(

        @NotBlank
        String nombreCompleto,

        @NotBlank
        String especialidad,

        @NotBlank
        String telefono,

        @NotBlank
        @Email
        String email

){}


