package com.abcall.agentes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AgentDtoTest {

    @Test
    void allArgsConstructorInitializesAllFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        AgentDto agent = new AgentDto();
        agent.setDocumentType(1);
        agent.setDocumentNumber("12345678");
        agent.setPassword("password123");
        agent.setNames("John");
        agent.setSurnames("Doe");
        agent.setLastLogin(now);

        assertEquals(1, agent.getDocumentType());
        assertEquals("12345678", agent.getDocumentNumber());
        assertEquals("password123", agent.getPassword());
        assertEquals("John", agent.getNames());
        assertEquals("Doe", agent.getSurnames());
        assertEquals(now, agent.getLastLogin());
    }

    @Test
    void noArgsConstructorInitializesFieldsToNull() {
        AgentDto agent = new AgentDto();

        assertNull(agent.getDocumentType());
        assertNull(agent.getDocumentNumber());
        assertNull(agent.getPassword());
        assertNull(agent.getNames());
        assertNull(agent.getSurnames());
        assertNull(agent.getLastLogin());
    }

    @Test
    void settersUpdateFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        AgentDto agent = new AgentDto();
        agent.setDocumentType(2);
        agent.setDocumentNumber("98765432");
        agent.setPassword("securePass");
        agent.setNames("Jane");
        agent.setSurnames("Smith");
        agent.setLastLogin(now);

        assertEquals(2, agent.getDocumentType());
        assertEquals("98765432", agent.getDocumentNumber());
        assertEquals("securePass", agent.getPassword());
        assertEquals("Jane", agent.getNames());
        assertEquals("Smith", agent.getSurnames());
        assertEquals(now, agent.getLastLogin());
    }
}