package com.abcall.clientes.persistence.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientTest {

    @Test
    void clientEntity_HasCorrectFieldValues() {
        Client client = getClient();

        assertEquals(1, client.getIdClient());
        assertEquals(123456789L, client.getDocumentNumber());
        assertEquals("password123", client.getPassword());
        assertEquals("ABC Corp", client.getSocialReason());
        assertEquals("abc@corp.com", client.getEmail());
        assertEquals("Premium", client.getPlan());
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0), client.getCreatedDate());
        assertEquals(LocalDateTime.of(2023, 1, 2, 0, 0), client.getUpdatedDate());
        assertEquals(LocalDateTime.of(2023, 1, 3, 0, 0), client.getLastLogin());
        assertEquals("Active", client.getStatus());
    }

    private static Client getClient() {
        Client client = new Client();
        client.setIdClient(1);
        client.setDocumentNumber(123456789L);
        client.setPassword("password123");
        client.setSocialReason("ABC Corp");
        client.setEmail("abc@corp.com");
        client.setPlan("Premium");
        client.setCreatedDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        client.setUpdatedDate(LocalDateTime.of(2023, 1, 2, 0, 0));
        client.setLastLogin(LocalDateTime.of(2023, 1, 3, 0, 0));
        client.setStatus("Active");
        return client;
    }

    @Test
    void clientEntity_AllowsNullValuesForOptionalFields() {
        Client client = new Client();
        client.setIdClient(1);
        client.setDocumentNumber(123456789L);
        client.setPassword("password123");
        client.setSocialReason("ABC Corp");
        client.setEmail("abc@corp.com");
        client.setPlan("Premium");
        client.setCreatedDate(null);
        client.setUpdatedDate(null);
        client.setLastLogin(null);
        client.setStatus(null);

        assertEquals(1, client.getIdClient());
        assertEquals(123456789L, client.getDocumentNumber());
        assertEquals("password123", client.getPassword());
        assertEquals("ABC Corp", client.getSocialReason());
        assertEquals("abc@corp.com", client.getEmail());
        assertEquals("Premium", client.getPlan());
        assertNull(client.getCreatedDate());
        assertNull(client.getUpdatedDate());
        assertNull(client.getLastLogin());
        assertNull(client.getStatus());
    }
}
