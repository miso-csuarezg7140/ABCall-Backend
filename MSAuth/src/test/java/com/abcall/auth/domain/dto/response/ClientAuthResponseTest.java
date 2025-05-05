package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAuthResponseTest {

    @Test
    void clientAuthResponseShouldStoreAllFieldsCorrectly() {
        ClientAuthResponse response = new ClientAuthResponse(1, "123456",
                List.of("ROLE_CLIENT"), true, "ABC Corp",
                "client@example.com");

        assertEquals(1, response.getClientId());
        assertEquals("123456", response.getDocumentNumber());
        assertEquals(List.of("ROLE_CLIENT"), response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("ABC Corp", response.getSocialReason());
        assertEquals("client@example.com", response.getEmail());
    }

    @Test
    void clientAuthResponseShouldHandleNullValues() {
        ClientAuthResponse response = new ClientAuthResponse(null, null, null,
                false, null, null);

        assertNull(response.getClientId());
        assertNull(response.getDocumentNumber());
        assertNull(response.getRoles());
        assertFalse(response.isAuthenticated());
        assertNull(response.getSocialReason());
        assertNull(response.getEmail());
    }

    @Test
    void clientAuthResponseShouldUpdateFieldsCorrectly() {
        ClientAuthResponse response = new ClientAuthResponse();

        response.setClientId(2);
        response.setDocumentNumber("654321");
        response.setRoles(List.of("ROLE_ADMIN"));
        response.setAuthenticated(true);
        response.setSocialReason("XYZ Ltd");
        response.setEmail("admin@example.com");

        assertEquals(2, response.getClientId());
        assertEquals("654321", response.getDocumentNumber());
        assertEquals(List.of("ROLE_ADMIN"), response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("XYZ Ltd", response.getSocialReason());
        assertEquals("admin@example.com", response.getEmail());
    }

    @Test
    void clientAuthResponseShouldHandleEmptyRolesList() {
        ClientAuthResponse response = new ClientAuthResponse(1, "123456", List.of(),
                true, "Company", "email@test.com");

        assertTrue(response.getRoles().isEmpty());
    }

    @Test
    void clientAuthResponseShouldCreateWithMultipleRoles() {
        List<String> roles = List.of("ROLE_CLIENT", "ROLE_ADMIN", "ROLE_USER");
        ClientAuthResponse response = new ClientAuthResponse(1, "123456", roles,
                true, "Company", "email@test.com");

        assertEquals(3, response.getRoles().size());
        assertEquals(roles, response.getRoles());
    }

    @Test
    void clientAuthResponseEqualsShouldWorkCorrectly() {
        ClientAuthResponse response1 = new ClientAuthResponse(1, "123", List.of("ROLE"),
                true, "Company", "email");
        ClientAuthResponse response2 = new ClientAuthResponse(1, "123", List.of("ROLE"),
                true, "Company", "email");
        ClientAuthResponse response3 = new ClientAuthResponse(2, "123", List.of("ROLE"),
                true, "Company", "email");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void clientAuthResponseShouldHandleNullLastLogin() {
        ClientAuthResponse response = new ClientAuthResponse(5, "123456",
                List.of("ROLE_CLIENT"), true, "Company Name", "email@company.com");

        assertEquals(5, response.getClientId());
        assertEquals("123456", response.getDocumentNumber());
        assertEquals(List.of("ROLE_CLIENT"), response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("Company Name", response.getSocialReason());
        assertEquals("email@company.com", response.getEmail());
    }

    @Test
    void clientAuthResponseShouldHandleEmptyDocumentNumber() {
        ClientAuthResponse response = new ClientAuthResponse(3, "", List.of("ROLE_CLIENT"),
                false, "Company", "email@test.com");

        assertEquals(3, response.getClientId());
        assertEquals("", response.getDocumentNumber());
        assertFalse(response.isAuthenticated());
    }

    @Test
    void clientAuthResponseShouldHandleNullRoles() {
        ClientAuthResponse response = new ClientAuthResponse(7, "654321", null,
                true, "Company", "email@test.com");

        assertEquals(7, response.getClientId());
        assertNull(response.getRoles());
        assertTrue(response.isAuthenticated());
    }
}