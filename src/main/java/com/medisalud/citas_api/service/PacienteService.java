package com.medisalud.citas_api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.medisalud.citas_api.dto.CreatePacienteRequest;
import com.medisalud.citas_api.entity.Paciente;
import com.medisalud.citas_api.exception.BusinessException;
import com.medisalud.citas_api.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository repository;

    public Paciente crear(CreatePacienteRequest request){

        if(request.fechaNacimiento().isAfter(LocalDate.now())){
            throw new BusinessException(
                    "La fecha de nacimiento no puede ser futura");
        }

        Paciente paciente = Paciente.builder()
                .nombreCompleto(request.nombreCompleto())
                .documento(request.documento())
                .telefono(request.telefono())
                .email(request.email())
                .fechaNacimiento(request.fechaNacimiento())
                .build();

        return repository.save(paciente);
    }

    public List<Paciente> listar(){
        return repository.findAll();
    }
}
