package com.abcall.agentes.persistence.repository;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;

public interface IAgentRepository {

    AgentDto findById(AgentPK agentPK);

    AgentDto save(AgentDto agent);
}