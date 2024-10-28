package com.abcall.agentes.persistence.repository;

import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;

public interface AgenteRepository {

    Agente obtenerPorId(AgentePK agentePK);
}
