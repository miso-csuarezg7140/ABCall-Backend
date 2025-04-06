package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAuthResponseTest {

    @Test
    void clientAuthResponseShouldStoreClientIdUsernameRolesAndAuthenticatedStatus() {
        ClientAuthResponse response = new ClientAuthResponse();
        response.setClientId(1);
        response.setUsername("client");
        response.setRoles(List.of("ROLE_USER", "ROLE_ADMIN"));
        response.setAuthenticated(true);
        assertEquals(1, response.getClientId());
        assertEquals("client", response.getUsername());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), response.getRoles());
        assertTrue(response.isAuthenticated());
    }

    @Test
    void clientAuthResponseShouldHandleEmptyRoles() {
        ClientAuthResponse response = new ClientAuthResponse();
        response.setRoles(List.of());
        assertEquals(List.of(), response.getRoles());
    }

    @Test
    void clientAuthResponseShouldHandleNullRoles() {
        ClientAuthResponse response = new ClientAuthResponse();
        response.setRoles(null);
        assertNull(response.getRoles());
    }

    @Test
    void clientAuthResponseShouldHandleNullClientIdUsernameAndAuthenticatedStatus() {
        ClientAuthResponse response = new ClientAuthResponse();
        response.setClientId(null);
        response.setUsername(null);
        response.setAuthenticated(false);
        assertNull(response.getClientId());
        assertNull(response.getUsername());
        assertFalse(response.isAuthenticated());
    }
}