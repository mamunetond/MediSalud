package com.medisalud.citas_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medisalud.citas_api.dto.CreateMedicoRequest;
import com.medisalud.citas_api.entity.Medico;
import com.medisalud.citas_api.repository.MedicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoRepository repository;

    public Medico crear(CreateMedicoRequest request){

        Medico medico = Medico.builder()
                .nombreCompleto(request.nombreCompleto())
                .especialidad(request.especialidad())
                .telefono(request.telefono())
                .email(request.email())
                .build();

        return repository.save(medico);
    }

    public List<Medico> listar(){
        return repository.findAll();
    }
}
