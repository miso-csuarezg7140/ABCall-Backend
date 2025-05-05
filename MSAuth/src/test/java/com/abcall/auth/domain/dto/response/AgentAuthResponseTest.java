package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgentAuthResponseTest {

    @Test
    void agentAuthResponseShouldStoreAllFieldsCorrectly() {
        AgentAuthResponse response = new AgentAuthResponse(1, "123456",
                List.of("ROLE_AGENT"), true, "John", "Doe");

        assertEquals(1, response.getDocumentType());
        assertEquals("123456", response.getDocumentNumber());
        assertEquals(List.of("ROLE_AGENT"), response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("John", response.getNames());
        assertEquals("Doe", response.getSurnames());
    }

    @Test
    void agentAuthResponseShouldHandleNullValues() {
        AgentAuthResponse response = new AgentAuthResponse(null, null, null,
                false, null, null);

        assertNull(response.getDocumentType());
        assertNull(response.getDocumentNumber());
        assertNull(response.getRoles());
        assertFalse(response.isAuthenticated());
        assertNull(response.getNames());
        assertNull(response.getSurnames());
    }

    @Test
    void agentAuthResponseShouldUpdateFieldsCorrectly() {
        AgentAuthResponse response = new AgentAuthResponse();

        response.setDocumentType(2);
        response.setDocumentNumber("654321");
        response.setRoles(List.of("ROLE_ADMIN"));
        response.setAuthenticated(true);
        response.setNames("Jane");
        response.setSurnames("Smith");

        assertEquals(2, response.getDocumentType());
        assertEquals("654321", response.getDocumentNumber());
        assertEquals(List.of("ROLE_ADMIN"), response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("Jane", response.getNames());
        assertEquals("Smith", response.getSurnames());
    }

    @Test
    void agentAuthResponseShouldHandleEmptyRolesList() {
        AgentAuthResponse response = new AgentAuthResponse(1, "123456", List.of(),
                true, "John", "Doe");

        assertTrue(response.getRoles().isEmpty());
    }

    @Test
    void agentAuthResponseShouldCreateWithMultipleRoles() {
        List<String> roles = List.of("ROLE_AGENT", "ROLE_ADMIN", "ROLE_USER");
        AgentAuthResponse response = new AgentAuthResponse(1, "123456", roles,
                true, "John", "Doe");

        assertEquals(3, response.getRoles().size());
        assertEquals(roles, response.getRoles());
    }

    @Test
    void agentAuthResponseEqualsShouldWorkCorrectly() {
        AgentAuthResponse response1 = new AgentAuthResponse(1, "123", List.of("ROLE"),
                true, "John", "Doe");
        AgentAuthResponse response2 = new AgentAuthResponse(1, "123", List.of("ROLE"),
                true, "John", "Doe");
        AgentAuthResponse response3 = new AgentAuthResponse(2, "123", List.of("ROLE"),
                true, "John", "Doe");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}