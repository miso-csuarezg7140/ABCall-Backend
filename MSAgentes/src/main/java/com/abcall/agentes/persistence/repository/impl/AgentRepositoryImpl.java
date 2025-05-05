package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.persistence.entity.Agent;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import com.abcall.agentes.persistence.mappers.IAgentMapper;
import com.abcall.agentes.persistence.repository.IAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AgentRepositoryImpl implements IAgentRepository {

    private final IAgentRepositoryJpa agentRepositoryJpa;
    private final IAgentMapper agentMapper;

    @Override
    public AgentDto findById(AgentPK agentPK) {
        Optional<Agent> agentOptional = agentRepositoryJpa.findById(agentPK);
        return agentOptional.map(agentMapper::toDto).orElse(null);
    }

    @Override
    public AgentDto save(AgentDto agentDto) {
        Agent agent = agentMapper.toEntity(agentDto);
        Agent agentSaved = agentRepositoryJpa.save(agent);
        return agentMapper.toDto(agentSaved);
    }
}