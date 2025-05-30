package com.abcall.clientes.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cliente", nullable = false)
    private Integer idClient;

    @Column(name = "numero_documento", nullable = false)
    private Long documentNumber;

    @Column(name = "contrasena", nullable = false)
    private String password;

    @Column(name = "razon_social", nullable = false)
    private String socialReason;

    @Column(name = "correo", nullable = false)
    private String email;

    @Column(name = "plan")
    private String plan;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "fecha_modificacion")
    private LocalDateTime updatedDate;

    @Column(name = "ultimo_login")
    private LocalDateTime lastLogin;

    @Column(name = "estado", nullable = false)
    private String status;
}
