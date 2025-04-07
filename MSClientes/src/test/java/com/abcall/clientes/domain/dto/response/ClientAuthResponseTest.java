package com.abcall.clientes.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAuthResponseTest {

    @Test
    void clientAuthResponse_HasCorrectFieldValues() {
        LocalDateTime now = LocalDateTime.now();
        ClientAuthResponse response = ClientAuthResponse.builder()
                .clientId(1)
                .documentNumber(123456789L)
                .authenticated(true)
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .lastLogin(now)
                .build();

        assertEquals(1, response.getClientId());
        assertEquals(123456789L, response.getDocumentNumber());
        assertTrue(response.isAuthenticated());
        assertEquals("ABC Corp", response.getSocialReason());
        assertEquals("abc@corp.com", response.getEmail());
        assertEquals(now, response.getLastLogin());
    }

    @Test
    void clientAuthResponse_AllowsNullValuesForOptionalFields() {
        ClientAuthResponse response = ClientAuthResponse.builder()
                .clientId(1)
                .documentNumber(123456789L)
                .authenticated(true)
                .socialReason(null)
                .email(null)
                .lastLogin(null)
                .build();

        assertEquals(1, response.getClientId());
        assertEquals(123456789L, response.getDocumentNumber());
        assertTrue(response.isAuthenticated());
        assertNull(response.getSocialReason());
        assertNull(response.getEmail());
        assertNull(response.getLastLogin());
    }

    @Test
    void clientAuthResponse_NoArgsConstructorCreatesEmptyObject() {
        ClientAuthResponse response = new ClientAuthResponse();

        assertNull(response.getClientId());
        assertNull(response.getDocumentNumber());
        assertFalse(response.isAuthenticated());
        assertNull(response.getSocialReason());
        assertNull(response.getEmail());
        assertNull(response.getLastLogin());
    }

    @Test
    void clientAuthResponse_AllArgsConstructorSetsAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ClientAuthResponse response = new ClientAuthResponse(1, 123456789L, true, "ABC Corp", "abc@corp.com", now);

        assertEquals(1, response.getClientId());
        assertEquals(123456789L, response.getDocumentNumber());
        assertTrue(response.isAuthenticated());
        assertEquals("ABC Corp", response.getSocialReason());
        assertEquals("abc@corp.com", response.getEmail());
        assertEquals(now, response.getLastLogin());
    }
}