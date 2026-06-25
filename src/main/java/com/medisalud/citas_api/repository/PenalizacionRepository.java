package com.medisalud.citas_api.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisalud.citas_api.entity.Penalizacion;

public interface PenalizacionRepository
        extends JpaRepository<Penalizacion, UUID> {

    long countByPacienteIdAndFechaRegistroAfter(
            UUID pacienteId,
            LocalDateTime fecha);
}