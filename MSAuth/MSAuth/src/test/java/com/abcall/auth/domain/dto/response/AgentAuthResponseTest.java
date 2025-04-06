package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgentAuthResponseTest {

    @Test
    void agentAuthResponseShouldStoreAgentIdUsernameRolesAndAuthenticatedStatus() {
        AgentAuthResponse response = new AgentAuthResponse();
        response.setAgentId(1);
        response.setUsername("agent");
        response.setRoles(List.of("ROLE_USER", "ROLE_ADMIN"));
        response.setAuthenticated(true);
        assertEquals(1, response.getAgentId());
        assertEquals("agent", response.getUsername());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), response.getRoles());
        assertTrue(response.isAuthenticated());
    }

    @Test
    void agentAuthResponseShouldHandleEmptyRoles() {
        AgentAuthResponse response = new AgentAuthResponse();
        response.setRoles(List.of());
        assertEquals(List.of(), response.getRoles());
    }

    @Test
    void agentAuthResponseShouldHandleNullRoles() {
        AgentAuthResponse response = new AgentAuthResponse();
        response.setRoles(null);
        assertNull(response.getRoles());
    }

    @Test
    void agentAuthResponseShouldHandleNullAgentIdUsernameAndAuthenticatedStatus() {
        AgentAuthResponse response = new AgentAuthResponse();
        response.setAgentId(null);
        response.setUsername(null);
        response.setAuthenticated(false);
        assertNull(response.getAgentId());
        assertNull(response.getUsername());
        assertFalse(response.isAuthenticated());
    }
}