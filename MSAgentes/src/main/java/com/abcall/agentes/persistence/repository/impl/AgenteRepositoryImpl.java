package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import com.abcall.agentes.persistence.repository.jpa.AgenteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AgenteRepositoryImpl implements AgenteRepository {

    private final AgenteRepositoryJpa agenteRepositoryJpa;

    @Override
    public Agente obtenerPorId(AgentePK agentePK) {
        return agenteRepositoryJpa.findById(agentePK).orElse(null);
    }
}
