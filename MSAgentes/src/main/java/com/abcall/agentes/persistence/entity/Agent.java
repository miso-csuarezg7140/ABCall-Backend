package com.abcall.agentes.persistence.entity;

import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "agente")
public class Agent {

    @EmbeddedId
    private AgentPK agentPK;

    @Column(name = "contrasena", nullable = false)
    private String password;

    @Column(name = "nombres", nullable = false)
    private String names;

    @Column(name = "apellidos", nullable = false)
    private String surnames;

    @Column(name = "ultimo_login")
    private LocalDateTime lastLogin;
}
