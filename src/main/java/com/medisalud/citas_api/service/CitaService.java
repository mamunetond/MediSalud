package com.medisalud.citas_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.medisalud.citas_api.dto.CitaRequest;
import com.medisalud.citas_api.entity.Cita;
import com.medisalud.citas_api.entity.EstadoCita;
import com.medisalud.citas_api.entity.Medico;
import com.medisalud.citas_api.entity.Paciente;
import com.medisalud.citas_api.entity.Penalizacion;
import com.medisalud.citas_api.exception.BusinessException;
import com.medisalud.citas_api.exception.ResourceNotFoundException;
import com.medisalud.citas_api.repository.CitaRepository;
import com.medisalud.citas_api.repository.MedicoRepository;
import com.medisalud.citas_api.repository.PacienteRepository;
import com.medisalud.citas_api.repository.PenalizacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final HorarioValidator horarioValidator;
    private final PenalizacionRepository penalizacionRepository;

    private void validarFechaNacimiento(Paciente paciente) {

        if (paciente.getFechaNacimiento() != null &&
                paciente.getFechaNacimiento().isAfter(LocalDate.now())) {

            throw new BusinessException(
                    "La fecha de nacimiento no puede ser futura");
        }
    }

    private void validarDisponibilidad(
            UUID medicoId,
            LocalDateTime fechaHora) {

        if (citaRepository.existsByMedicoIdAndFechaHora(
                medicoId,
                fechaHora)) {

            throw new BusinessException(
                    "El médico ya tiene una cita en ese horario");
        }
    }

    private void validarConflictoPaciente(
            UUID pacienteId,
            UUID medicoId,
            LocalDateTime fechaHora) {

        if (citaRepository
                .existsByPacienteIdAndMedicoIdAndFechaHora(
                        pacienteId,
                        medicoId,
                        fechaHora)) {

            throw new BusinessException(
                    "El paciente ya tiene una cita con este médico en ese horario");
        }
    }

    public Cita crear(CitaRequest request) {

        Medico medico = medicoRepository
                .findById(request.getMedicoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Médico no encontrado"));

        Paciente paciente = pacienteRepository
                .findById(request.getPacienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente no encontrado"));
        
        validarPenalizaciones(paciente);

        validarFechaNacimiento(paciente);

        horarioValidator.validarHorario(
                request.getFechaHora());

        horarioValidator.validarFranja(
                request.getFechaHora());

        validarDisponibilidad(
                medico.getId(),
                request.getFechaHora());

        validarConflictoPaciente(
                paciente.getId(),
                medico.getId(),
                request.getFechaHora());

        Cita cita = new Cita();

        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(request.getFechaHora());
        cita.setEstado(EstadoCita.PROGRAMADA);

        return citaRepository.save(cita);
    }

    public Cita cancelar(Long citaId) {

    Cita cita = citaRepository.findById(citaId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Cita no encontrada"));

    if (cita.getEstado() == EstadoCita.CANCELADA) {

        throw new BusinessException(
                "La cita ya fue cancelada");
    }

    long horas =
            java.time.Duration.between(
                    LocalDateTime.now(),
                    cita.getFechaHora())
                    .toHours();

    if (horas < 24) {

        Penalizacion penalizacion =
                Penalizacion.builder()
                        .paciente(cita.getPaciente())
                        .fechaRegistro(LocalDateTime.now())
                        .motivo("Cancelación tardía")
                        .build();

        penalizacionRepository.save(penalizacion);
    }

    cita.setEstado(EstadoCita.CANCELADA);
    cita.setFechaCancelacion(LocalDateTime.now());

    return citaRepository.save(cita);
}

    private void validarPenalizaciones(Paciente paciente) {

    long penalizaciones =
            penalizacionRepository
                    .countByPacienteIdAndFechaRegistroAfter(
                            paciente.getId(),
                            LocalDateTime.now().minusDays(30));

    if (penalizaciones >= 3) {

        throw new BusinessException(
                "El paciente tiene 3 o más penalizaciones activas");
    }
}

    public List<Cita> listarTodas() {
    return citaRepository.findAll();
}
    public List<Cita> listarPorMedico(UUID medicoId) {
    return citaRepository.findByMedicoId(medicoId);
}

public List<Cita> listarPorPaciente(UUID pacienteId) {
    return citaRepository.findByPacienteId(pacienteId);
}

public List<Cita> listarPorEstado(EstadoCita estado) {
    return citaRepository.findByEstado(estado);
}
}