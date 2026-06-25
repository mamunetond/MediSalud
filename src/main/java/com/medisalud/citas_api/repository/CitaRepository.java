package com.medisalud.citas_api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medisalud.citas_api.entity.Cita;
import com.medisalud.citas_api.entity.EstadoCita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByMedicoIdAndFechaHora(
            UUID medicoId,
            LocalDateTime fechaHora
    );

    boolean existsByPacienteIdAndMedicoIdAndFechaHora(
            UUID pacienteId,
            UUID medicoId,
            LocalDateTime fechaHora
    );
    
    List<Cita> findByEstado(EstadoCita estado);
    List<Cita> findByMedicoId(UUID medicoId);
    List<Cita> findByPacienteId(UUID pacienteId);
}