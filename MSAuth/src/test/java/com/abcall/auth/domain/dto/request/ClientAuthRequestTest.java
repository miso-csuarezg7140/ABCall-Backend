package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientAuthRequestTest {

    @Test
    void clientAuthRequestShouldStoreAllFieldsCorrectly() {
        ClientAuthRequest request = new ClientAuthRequest("12345", "password123");

        assertEquals("12345", request.getDocumentNumber());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void clientAuthRequestShouldHandleNullValues() {
        ClientAuthRequest request = new ClientAuthRequest(null, null);

        assertNull(request.getDocumentNumber());
        assertNull(request.getPassword());
    }

    @Test
    void clientAuthRequestShouldUpdateFieldsCorrectly() {
        ClientAuthRequest request = new ClientAuthRequest("12345", "password123");

        request.setDocumentNumber("67890");
        request.setPassword("newPassword");

        assertEquals("67890", request.getDocumentNumber());
        assertEquals("newPassword", request.getPassword());
    }
}