package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgentJwtResponseTest {

    @Test
    void shouldInitializeFieldsCorrectly() {
        AgentJwtResponse agentJwtResponse = new AgentJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_AGENT"),
                "12345678",
                1,
                "John",
                "Doe"
        );

        assertEquals("sampleToken", agentJwtResponse.getToken());
        assertEquals("Bearer", agentJwtResponse.getType());
        assertEquals("sampleRefreshToken", agentJwtResponse.getRefreshToken());
        assertEquals(3600L, agentJwtResponse.getExpiresIn());
        assertEquals(List.of("ROLE_AGENT"), agentJwtResponse.getRoles());
        assertEquals("12345678", agentJwtResponse.getDocumentNumber());
        assertEquals(1, agentJwtResponse.getDocumentType());
        assertEquals("John", agentJwtResponse.getNames());
        assertEquals("Doe", agentJwtResponse.getSurnames());
    }

    @Test
    void shouldHandleNullRoles() {
        AgentJwtResponse agentJwtResponse = new AgentJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                null,
                "12345678",
                1,
                "John",
                "Doe"
        );

        assertNull(agentJwtResponse.getRoles());
    }

    @Test
    void shouldHandleEmptyRoles() {
        AgentJwtResponse agentJwtResponse = new AgentJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of(),
                "12345678",
                1,
                "John",
                "Doe"
        );

        assertTrue(agentJwtResponse.getRoles().isEmpty());
    }

    @Test
    void shouldHandleNullDocumentNumber() {
        AgentJwtResponse agentJwtResponse = new AgentJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_AGENT"),
                null,
                1,
                "John",
                "Doe"
        );

        assertNull(agentJwtResponse.getDocumentNumber());
    }

    @Test
    void shouldHandleNullNamesAndSurnames() {
        AgentJwtResponse agentJwtResponse = new AgentJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_AGENT"),
                "12345678",
                1,
                null,
                null
        );

        assertNull(agentJwtResponse.getNames());
        assertNull(agentJwtResponse.getSurnames());
    }
}