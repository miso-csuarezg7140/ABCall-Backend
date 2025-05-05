package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.persistence.entity.Agent;
import com.abcall.agentes.persistence.entity.compositekey.AgentPK;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AgentMapperImplTest {

    @Test
    void toDtoReturnsNullWhenAgentIsNull() {
        IAgentMapperImpl mapper = new IAgentMapperImpl();

        AgentDto result = mapper.toDto(null);

        assertNull(result);
    }

    @Test
    void toDtoMapsAgentToAgentDtoCorrectly() {
        AgentPK agentPK = new AgentPK(1, "12345678");
        Agent agent = new Agent();
        agent.setAgentPK(agentPK);
        agent.setPassword("password123");
        agent.setNames("John");
        agent.setSurnames("Doe");
        agent.setLastLogin(LocalDateTime.now());

        IAgentMapperImpl mapper = new IAgentMapperImpl();

        AgentDto result = mapper.toDto(agent);

        assertEquals(agentPK.getDocumentType(), result.getDocumentType());
        assertEquals(agentPK.getDocumentNumber(), result.getDocumentNumber());
        assertEquals(agent.getPassword(), result.getPassword());
        assertEquals(agent.getNames(), result.getNames());
        assertEquals(agent.getSurnames(), result.getSurnames());
        assertEquals(agent.getLastLogin(), result.getLastLogin());
    }

    @Test
    void toEntityReturnsNullWhenAgentDtoIsNull() {
        IAgentMapperImpl mapper = new IAgentMapperImpl();

        Agent result = mapper.toEntity(null);

        assertNull(result);
    }

    @Test
    void toEntityMapsAgentDtoToAgentCorrectly() {
        AgentDto agentDto = new AgentDto();
        agentDto.setDocumentType(1);
        agentDto.setDocumentNumber("12345678");
        agentDto.setPassword("password123");
        agentDto.setNames("John");
        agentDto.setSurnames("Doe");
        agentDto.setLastLogin(LocalDateTime.now());

        IAgentMapperImpl mapper = new IAgentMapperImpl();

        Agent result = mapper.toEntity(agentDto);

        assertEquals(agentDto.getDocumentType(), result.getAgentPK().getDocumentType());
        assertEquals(agentDto.getDocumentNumber(), result.getAgentPK().getDocumentNumber());
        assertEquals(agentDto.getPassword(), result.getPassword());
        assertEquals(agentDto.getNames(), result.getNames());
        assertEquals(agentDto.getSurnames(), result.getSurnames());
        assertEquals(agentDto.getLastLogin(), result.getLastLogin());
    }

    @Test
    void toDtoHandlesAgentWithNullAgentPK() {
        Agent agent = new Agent();
        agent.setPassword("password123");
        agent.setNames("John");
        agent.setSurnames("Doe");
        agent.setLastLogin(LocalDateTime.now());

        IAgentMapperImpl mapper = new IAgentMapperImpl();

        AgentDto result = mapper.toDto(agent);

        assertNull(result.getDocumentType());
        assertNull(result.getDocumentNumber());
        assertEquals(agent.getPassword(), result.getPassword());
        assertEquals(agent.getNames(), result.getNames());
        assertEquals(agent.getSurnames(), result.getSurnames());
        assertEquals(agent.getLastLogin(), result.getLastLogin());
    }

    @Test
    void toEntityHandlesAgentDtoWithNullFields() {
        AgentDto agentDto = new AgentDto();

        IAgentMapperImpl mapper = new IAgentMapperImpl();

        Agent result = mapper.toEntity(agentDto);

        assertNull(result.getAgentPK().getDocumentType());
        assertNull(result.getAgentPK().getDocumentNumber());
        assertNull(result.getPassword());
        assertNull(result.getNames());
        assertNull(result.getSurnames());
        assertNull(result.getLastLogin());
    }
}