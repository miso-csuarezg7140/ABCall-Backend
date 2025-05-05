package com.abcall.clientes.domain.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ListClientResponseTest {

    @Test
    void listClientResponse_HasCorrectValues_WhenFieldsAreSet() {
        ListClientResponse response = new ListClientResponse();
        response.setDocumentNumber(123456789L);
        response.setSocialReason("Test Company");

        assertEquals(123456789L, response.getDocumentNumber());
        assertEquals("Test Company", response.getSocialReason());
    }

    @Test
    void listClientResponse_ReturnsNullValues_WhenFieldsAreNotSet() {
        ListClientResponse response = new ListClientResponse();

        assertNull(response.getDocumentNumber());
        assertNull(response.getSocialReason());
    }

    @Test
    void listClientResponse_AllowsFieldModification_AfterInitialSet() {
        ListClientResponse response = new ListClientResponse();
        response.setDocumentNumber(123456789L);
        response.setSocialReason("Test Company");

        response.setDocumentNumber(987654321L);
        response.setSocialReason("Updated Company");

        assertEquals(987654321L, response.getDocumentNumber());
        assertEquals("Updated Company", response.getSocialReason());
    }
}