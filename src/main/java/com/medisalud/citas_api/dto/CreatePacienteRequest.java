package com.medisalud.citas_api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record CreatePacienteRequest(

        @NotBlank
        String nombreCompleto,

        @NotBlank
        String documento,

        @NotBlank
        String telefono,

        @NotBlank
        @Email
        String email,

        @Past
        LocalDate fechaNacimiento
){}
