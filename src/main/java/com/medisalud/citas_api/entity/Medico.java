package com.medisalud.citas_api.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Medico {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String especialidad;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;
}
