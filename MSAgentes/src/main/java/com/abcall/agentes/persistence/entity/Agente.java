package com.abcall.agentes.persistence.entity;

import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agente")
public class Agente {

    @EmbeddedId
    private AgentePK agentePK;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;
}
