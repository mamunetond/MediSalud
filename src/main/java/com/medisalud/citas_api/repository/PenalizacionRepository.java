package com.medisalud.citas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medisalud.citas_api.entity.Penalizacion;

public interface PenalizacionRepository extends JpaRepository<Penalizacion, Long>{
}
