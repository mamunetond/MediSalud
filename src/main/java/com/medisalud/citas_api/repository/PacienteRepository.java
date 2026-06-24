package com.medisalud.citas_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisalud.citas_api.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID>{
}
