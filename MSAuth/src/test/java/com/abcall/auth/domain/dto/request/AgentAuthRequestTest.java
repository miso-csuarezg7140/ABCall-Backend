package com.abcall.auth.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AgentAuthRequestTest {

    @Test
    void agentAuthRequestShouldStoreAllFieldsCorrectly() {
        AgentAuthRequest request = new AgentAuthRequest("ID", "12345", "password123");

        assertEquals("ID", request.getDocumentType());
        assertEquals("12345", request.getDocumentNumber());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void agentAuthRequestShouldHandleNullValues() {
        AgentAuthRequest request = new AgentAuthRequest(null, null, null);

        assertNull(request.getDocumentType());
        assertNull(request.getDocumentNumber());
        assertNull(request.getPassword());
    }

    @Test
    void agentAuthRequestShouldUpdateFieldsCorrectly() {
        AgentAuthRequest request = new AgentAuthRequest("ID", "12345", "password123");

        request.setDocumentType("Passport");
        request.setDocumentNumber("67890");
        request.setPassword("newPassword");

        assertEquals("Passport", request.getDocumentType());
        assertEquals("67890", request.getDocumentNumber());
        assertEquals("newPassword", request.getPassword());
    }
}