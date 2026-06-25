package com.medisalud.citas_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PenalizacionRepository penalizacionRepository;

    @Mock
    private HorarioValidator horarioValidator;

    @InjectMocks
    private CitaService citaService;

    @Test
    void debeCrearCitaCorrectamente() {

        UUID medicoId = UUID.randomUUID();
        UUID pacienteId = UUID.randomUUID();

        LocalDateTime fecha =
                LocalDateTime.now().plusDays(1);

        Medico medico = Medico.builder()
                .id(medicoId)
                .nombreCompleto("Juan Perez")
                .build();

        Paciente paciente = Paciente.builder()
                .id(pacienteId)
                .nombreCompleto("Maria Gomez")
                .fechaNacimiento(
                        LocalDate.of(1990, 1, 1))
                .build();

        CitaRequest request = new CitaRequest();
        request.setMedicoId(medicoId);
        request.setPacienteId(pacienteId);
        request.setFechaHora(fecha);

        when(medicoRepository.findById(medicoId))
                .thenReturn(Optional.of(medico));

        when(pacienteRepository.findById(pacienteId))
                .thenReturn(Optional.of(paciente));

        when(citaRepository.existsByMedicoIdAndFechaHora(
                medicoId,
                fecha))
                .thenReturn(false);

        when(citaRepository
                .existsByPacienteIdAndMedicoIdAndFechaHora(
                        pacienteId,
                        medicoId,
                        fecha))
                .thenReturn(false);

        when(penalizacionRepository
                .countByPacienteIdAndFechaRegistroAfter(
                        any(),
                        any()))
                .thenReturn(0L);

        when(citaRepository.save(any(Cita.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Cita cita = citaService.crear(request);

        assertNotNull(cita);
        assertEquals(
                EstadoCita.PROGRAMADA,
                cita.getEstado());
    }

    @Test
    void debeLanzarExcepcionCuandoMedicoEstaOcupado() {

        UUID medicoId = UUID.randomUUID();
        UUID pacienteId = UUID.randomUUID();

        LocalDateTime fecha =
                LocalDateTime.now().plusDays(1);

        Medico medico = Medico.builder()
                .id(medicoId)
                .build();

        Paciente paciente = Paciente.builder()
                .id(pacienteId)
                .fechaNacimiento(
                        LocalDate.of(1990, 1, 1))
                .build();

        CitaRequest request = new CitaRequest();
        request.setMedicoId(medicoId);
        request.setPacienteId(pacienteId);
        request.setFechaHora(fecha);

        when(medicoRepository.findById(medicoId))
                .thenReturn(Optional.of(medico));

        when(pacienteRepository.findById(pacienteId))
                .thenReturn(Optional.of(paciente));

        when(penalizacionRepository
                .countByPacienteIdAndFechaRegistroAfter(
                        any(),
                        any()))
                .thenReturn(0L);

        when(citaRepository.existsByMedicoIdAndFechaHora(
                medicoId,
                fecha))
                .thenReturn(true);

        assertThrows(
                BusinessException.class,
                () -> citaService.crear(request));
    }

    @Test
void debeLanzarExcepcionCuandoPacienteNoExiste() {

    UUID medicoId = UUID.randomUUID();
    UUID pacienteId = UUID.randomUUID();

    CitaRequest request = new CitaRequest();

    request.setMedicoId(medicoId);
    request.setPacienteId(pacienteId);
    request.setFechaHora(
            LocalDateTime.now().plusDays(1));

    Medico medico = Medico.builder()
            .id(medicoId)
            .build();

    when(medicoRepository.findById(medicoId))
            .thenReturn(Optional.of(medico));

    when(pacienteRepository.findById(pacienteId))
            .thenReturn(Optional.empty());

    assertThrows(
            ResourceNotFoundException.class,
            () -> citaService.crear(request));
}

@Test
void debeLanzarExcepcionCuandoMedicoNoExiste() {

    UUID medicoId = UUID.randomUUID();
    UUID pacienteId = UUID.randomUUID();

    CitaRequest request = new CitaRequest();

    request.setMedicoId(medicoId);
    request.setPacienteId(pacienteId);
    request.setFechaHora(
            LocalDateTime.now().plusDays(1));

    when(medicoRepository.findById(medicoId))
            .thenReturn(Optional.empty());

    assertThrows(
            ResourceNotFoundException.class,
            () -> citaService.crear(request));
}

@Test
void debeLanzarExcepcionCuandoPacienteTienePenalizaciones() {

    UUID medicoId = UUID.randomUUID();
    UUID pacienteId = UUID.randomUUID();

    Medico medico = Medico.builder()
            .id(medicoId)
            .build();

    Paciente paciente = Paciente.builder()
            .id(pacienteId)
            .fechaNacimiento(
                    LocalDate.of(1990, 1, 1))
            .build();

    CitaRequest request = new CitaRequest();

    request.setMedicoId(medicoId);
    request.setPacienteId(pacienteId);
    request.setFechaHora(
            LocalDateTime.now().plusDays(1));

    when(medicoRepository.findById(medicoId))
            .thenReturn(Optional.of(medico));

    when(pacienteRepository.findById(pacienteId))
            .thenReturn(Optional.of(paciente));

    when(penalizacionRepository
            .countByPacienteIdAndFechaRegistroAfter(
                    any(),
                    any()))
            .thenReturn(3L);

    assertThrows(
            BusinessException.class,
            () -> citaService.crear(request));
}

@Test
void debeCancelarCitaCorrectamente() {

    Cita cita = new Cita();

    cita.setId(1L);
    cita.setEstado(EstadoCita.PROGRAMADA);
    cita.setFechaHora(
            LocalDateTime.now().plusDays(2));

    when(citaRepository.findById(1L))
            .thenReturn(Optional.of(cita));

    when(citaRepository.save(any()))
            .thenAnswer(inv -> inv.getArgument(0));

    Cita resultado =
            citaService.cancelar(1L);

    assertEquals(
            EstadoCita.CANCELADA,
            resultado.getEstado());

    assertNotNull(
            resultado.getFechaCancelacion());
}

@Test
void debeLanzarExcepcionCuandoLaCitaYaEstaCancelada() {

    Cita cita = new Cita();

    cita.setEstado(
            EstadoCita.CANCELADA);

    when(citaRepository.findById(1L))
            .thenReturn(Optional.of(cita));

    assertThrows(
            BusinessException.class,
            () -> citaService.cancelar(1L));
}

@Test
void debeCrearPenalizacionCuandoCancelacionEsMenorA24Horas() {

    Paciente paciente = Paciente.builder()
            .id(UUID.randomUUID())
            .build();

    Cita cita = new Cita();

    cita.setEstado(
            EstadoCita.PROGRAMADA);

    cita.setPaciente(paciente);

    cita.setFechaHora(
            LocalDateTime.now().plusHours(5));

    when(citaRepository.findById(1L))
            .thenReturn(Optional.of(cita));

    when(citaRepository.save(any()))
            .thenAnswer(inv -> inv.getArgument(0));

    citaService.cancelar(1L);

    verify(penalizacionRepository)
            .save(any(Penalizacion.class));
}
}