package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.persistence.entity.Agent;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import com.abcall.agentes.persistence.mappers.IAgentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentRepositoryImplTest {

    @Mock
    private IAgentRepositoryJpa agentRepositoryJpa;

    @Mock
    private IAgentMapper agentMapper;

    @InjectMocks
    private AgentRepositoryImpl agentRepository;

    @Test
    void findByIdReturnsNullWhenAgentNotFound() {
        AgentPK agentPK = new AgentPK(1, "12345678");
        when(agentRepositoryJpa.findById(agentPK)).thenReturn(Optional.empty());

        AgentDto result = agentRepository.findById(agentPK);

        assertNull(result);
        verify(agentRepositoryJpa).findById(agentPK);
        verifyNoInteractions(agentMapper);
    }

    @Test
    void findByIdReturnsAgentDtoWhenAgentFound() {
        AgentPK agentPK = new AgentPK(1, "12345678");
        Agent agent = new Agent();
        AgentDto expectedDto = new AgentDto();

        when(agentRepositoryJpa.findById(agentPK)).thenReturn(Optional.of(agent));
        when(agentMapper.toDto(agent)).thenReturn(expectedDto);

        AgentDto result = agentRepository.findById(agentPK);

        assertEquals(expectedDto, result);
        verify(agentRepositoryJpa).findById(agentPK);
        verify(agentMapper).toDto(agent);
    }

    @Test
    void savePersistsNewAgentAndReturnsDto() {
        AgentDto inputDto = new AgentDto();
        Agent mappedAgent = new Agent();
        Agent savedAgent = new Agent();
        AgentDto expectedDto = new AgentDto();

        when(agentMapper.toEntity(inputDto)).thenReturn(mappedAgent);
        when(agentRepositoryJpa.save(mappedAgent)).thenReturn(savedAgent);
        when(agentMapper.toDto(savedAgent)).thenReturn(expectedDto);

        AgentDto result = agentRepository.save(inputDto);

        assertEquals(expectedDto, result);
        verify(agentMapper).toEntity(inputDto);
        verify(agentRepositoryJpa).save(mappedAgent);
        verify(agentMapper).toDto(savedAgent);
    }
}