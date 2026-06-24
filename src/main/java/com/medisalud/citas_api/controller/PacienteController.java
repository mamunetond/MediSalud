package com.medisalud.citas_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisalud.citas_api.dto.CreatePacienteRequest;
import com.medisalud.citas_api.entity.Paciente;
import com.medisalud.citas_api.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService pacienteService;

    @PostMapping
    public Paciente crear(@Valid @RequestBody CreatePacienteRequest request) {
        return pacienteService.crear(request);
    }

    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listar();
    }
}
