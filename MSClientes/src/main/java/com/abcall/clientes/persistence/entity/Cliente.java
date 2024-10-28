package com.abcall.clientes.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "numero_documento", nullable = false)
    private Long numeroDocumento;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "plan", nullable = false)
    private String plan;

    @Column(name = "creado_por", nullable = false)
    private String creadoPor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "estado", nullable = false)
    private String estado;
}
