package com.medisalud.citas_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medisalud.citas_api.dto.CitaRequest;
import com.medisalud.citas_api.entity.Cita;
import com.medisalud.citas_api.entity.EstadoCita;
import com.medisalud.citas_api.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @Operation(summary = "Crear una cita")
    @PostMapping
    public ResponseEntity<Cita> crear(
            @Valid @RequestBody CitaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(citaService.crear(request));
    }

    @Operation(summary = "Cancelar una cita")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                citaService.cancelar(id));
    }

    @Operation(summary = "Listar todas las citas")
    @GetMapping
    public ResponseEntity<List<Cita>> listarTodas() {

        return ResponseEntity.ok(
                citaService.listarTodas());
    }

    @Operation(summary = "Listar citas por médico")
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Cita>> listarPorMedico(
            @PathVariable UUID medicoId) {

        return ResponseEntity.ok(
                citaService.listarPorMedico(medicoId));
    }

    @Operation(summary = "Listar citas por paciente")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> listarPorPaciente(
            @PathVariable UUID pacienteId) {

        return ResponseEntity.ok(
                citaService.listarPorPaciente(pacienteId));
    }

    @Operation(summary = "Listar citas por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cita>> listarPorEstado(
            @PathVariable EstadoCita estado) {

        return ResponseEntity.ok(
                citaService.listarPorEstado(estado));
    }
}