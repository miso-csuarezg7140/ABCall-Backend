package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.persistence.entity.Agent;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAgentRepositoryJpa extends JpaRepository<Agent, AgentPK> {
}
