package com.abcall.clientes.domain.dto;

import com.abcall.clientes.util.Constants;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientDtoTest {

    @Test
    void clientDto_HasCorrectFieldValues() {
        LocalDateTime now = LocalDateTime.now();
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(1);
        clientDto.setDocumentNumber(123456789L);
        clientDto.setPassword("password123");
        clientDto.setSocialReason("ABC Corp");
        clientDto.setEmail("abc@corp.com");
        clientDto.setPlan("Premium");
        clientDto.setCreatedDate(now);
        clientDto.setUpdatedDate(now);
        clientDto.setLastLogin(now);
        clientDto.setStatus('A');

        assertEquals(1, clientDto.getClientId());
        assertEquals(123456789L, clientDto.getDocumentNumber());
        assertEquals("password123", clientDto.getPassword());
        assertEquals("ABC Corp", clientDto.getSocialReason());
        assertEquals("abc@corp.com", clientDto.getEmail());
        assertEquals("Premium", clientDto.getPlan());
        assertEquals(now, clientDto.getCreatedDate());
        assertEquals(now, clientDto.getUpdatedDate());
        assertEquals(now, clientDto.getLastLogin());
        assertEquals('A', clientDto.getStatus());
    }

    @Test
    void clientDto_AllowsNullValuesForOptionalFields() {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(1);
        clientDto.setDocumentNumber(123456789L);
        clientDto.setPassword("password123");
        clientDto.setSocialReason("ABC Corp");
        clientDto.setEmail("abc@corp.com");
        clientDto.setPlan("Premium");
        clientDto.setCreatedDate(null);
        clientDto.setUpdatedDate(null);
        clientDto.setLastLogin(null);
        clientDto.setStatus(null);

        assertEquals(1, clientDto.getClientId());
        assertEquals(123456789L, clientDto.getDocumentNumber());
        assertEquals("password123", clientDto.getPassword());
        assertEquals("ABC Corp", clientDto.getSocialReason());
        assertEquals("abc@corp.com", clientDto.getEmail());
        assertEquals("Premium", clientDto.getPlan());
        assertNull(clientDto.getCreatedDate());
        assertNull(clientDto.getUpdatedDate());
        assertNull(clientDto.getLastLogin());
        assertNull(clientDto.getStatus());
    }

    @Test
    void clientDto_DefaultValuesAreSetCorrectly() {
        ClientDto clientDto = new ClientDto();

        assertEquals(Constants.HOY, clientDto.getCreatedDate());
        assertEquals(Constants.ESTADO_DEFAULT, clientDto.getStatus());
    }
}