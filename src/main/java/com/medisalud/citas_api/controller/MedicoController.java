package com.medisalud.citas_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisalud.citas_api.dto.CreateMedicoRequest;
import com.medisalud.citas_api.entity.Medico;
import com.medisalud.citas_api.service.MedicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final MedicoService medicoService;

    @PostMapping
    public Medico crear(@Valid @RequestBody CreateMedicoRequest request) {
        return medicoService.crear(request);
    }

    @GetMapping
    public List<Medico> listar() {
        return medicoService.listar();
    }
}
