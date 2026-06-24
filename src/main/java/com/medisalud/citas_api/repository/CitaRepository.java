package com.medisalud.citas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisalud.citas_api.entity.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{
}
