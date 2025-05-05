package com.abcall.clientes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ClientAuthenticationInfoTest {

    @Test
    void buildsCorrectlyWithAllFields() {
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");

        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .subject("testSubject")
                .clientId(123)
                .documentNumber(456789L)
                .socialReason("Test Social Reason")
                .email("test@example.com")
                .roles(roles)
                .build();

        assertEquals("testSubject", info.getSubject());
        assertEquals(123, info.getClientId());
        assertEquals(456789L, info.getDocumentNumber());
        assertEquals("Test Social Reason", info.getSocialReason());
        assertEquals("test@example.com", info.getEmail());
        assertEquals(roles, info.getRoles());
    }

    @Test
    void buildsCorrectlyWithNullFields() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .subject(null)
                .clientId(null)
                .documentNumber(null)
                .socialReason(null)
                .email(null)
                .roles(null)
                .build();

        assertNull(info.getSubject());
        assertNull(info.getClientId());
        assertNull(info.getDocumentNumber());
        assertNull(info.getSocialReason());
        assertNull(info.getEmail());
        assertNull(info.getRoles());
    }

    @Test
    void updatesFieldsCorrectly() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder().build();

        info.setSubject("updatedSubject");
        info.setClientId(456);
        info.setDocumentNumber(987654L);
        info.setSocialReason("Updated Social Reason");
        info.setEmail("updated@example.com");
        info.setRoles(List.of("ROLE_UPDATED"));

        assertEquals("updatedSubject", info.getSubject());
        assertEquals(456, info.getClientId());
        assertEquals(987654L, info.getDocumentNumber());
        assertEquals("Updated Social Reason", info.getSocialReason());
        assertEquals("updated@example.com", info.getEmail());
        assertEquals(List.of("ROLE_UPDATED"), info.getRoles());
    }
}