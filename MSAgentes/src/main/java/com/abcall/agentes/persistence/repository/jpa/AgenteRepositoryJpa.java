package com.abcall.agentes.persistence.repository.jpa;

import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenteRepositoryJpa extends JpaRepository<Agente, AgentePK> {
}
