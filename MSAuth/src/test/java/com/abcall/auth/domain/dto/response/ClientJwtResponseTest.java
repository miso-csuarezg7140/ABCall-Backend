package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientJwtResponseTest {

    @Test
    void shouldInitializeFieldsCorrectly() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_CLIENT"),
                123,
                "12345678",
                "Sample Social Reason",
                "sample@example.com"
        );

        assertEquals("sampleToken", clientJwtResponse.getToken());
        assertEquals("Bearer", clientJwtResponse.getType());
        assertEquals("sampleRefreshToken", clientJwtResponse.getRefreshToken());
        assertEquals(3600L, clientJwtResponse.getExpiresIn());
        assertEquals(List.of("ROLE_CLIENT"), clientJwtResponse.getRoles());
        assertEquals(123, clientJwtResponse.getClientId());
        assertEquals("12345678", clientJwtResponse.getDocumentNumber());
        assertEquals("Sample Social Reason", clientJwtResponse.getSocialReason());
        assertEquals("sample@example.com", clientJwtResponse.getEmail());
    }

    @Test
    void shouldHandleNullRoles() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                null,
                123,
                "12345678",
                "Sample Social Reason",
                "sample@example.com"
        );

        assertNull(clientJwtResponse.getRoles());
    }

    @Test
    void shouldHandleEmptyRoles() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of(),
                123,
                "12345678",
                "Sample Social Reason",
                "sample@example.com"
        );

        assertTrue(clientJwtResponse.getRoles().isEmpty());
    }

    @Test
    void shouldHandleNullClientId() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_CLIENT"),
                null,
                "12345678",
                "Sample Social Reason",
                "sample@example.com"
        );

        assertNull(clientJwtResponse.getClientId());
    }

    @Test
    void shouldHandleNullDocumentNumber() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_CLIENT"),
                123,
                null,
                "Sample Social Reason",
                "sample@example.com"
        );

        assertNull(clientJwtResponse.getDocumentNumber());
    }

    @Test
    void shouldHandleNullSocialReasonAndEmail() {
        ClientJwtResponse clientJwtResponse = new ClientJwtResponse(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_CLIENT"),
                123,
                "12345678",
                null,
                null
        );

        assertNull(clientJwtResponse.getSocialReason());
        assertNull(clientJwtResponse.getEmail());
    }
}