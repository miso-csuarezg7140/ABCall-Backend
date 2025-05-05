package com.abcall.agentes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AgentAuthenticationInfoTest {

    @Test
    void builderInitializesAllFieldsCorrectly() {
        List<String> roles = List.of("ADMIN", "USER");

        AgentAuthenticationInfo info = AgentAuthenticationInfo.builder()
                .subject("subject123")
                .documentType(1)
                .documentNumber("12345678")
                .names("John")
                .surnames("Doe")
                .roles(roles)
                .build();

        assertEquals("subject123", info.getSubject());
        assertEquals(1, info.getDocumentType());
        assertEquals("12345678", info.getDocumentNumber());
        assertEquals("John", info.getNames());
        assertEquals("Doe", info.getSurnames());
        assertEquals(roles, info.getRoles());
    }

    @Test
    void builderHandlesNullRoles() {
        AgentAuthenticationInfo info = AgentAuthenticationInfo.builder()
                .subject("subject123")
                .documentType(1)
                .documentNumber("12345678")
                .names("John")
                .surnames("Doe")
                .roles(null)
                .build();

        assertNull(info.getRoles());
    }

    @Test
    void builderHandlesEmptyRolesList() {
        AgentAuthenticationInfo info = AgentAuthenticationInfo.builder()
                .subject("subject123")
                .documentType(1)
                .documentNumber("12345678")
                .names("John")
                .surnames("Doe")
                .roles(List.of())
                .build();

        assertNotNull(info.getRoles());
        assertTrue(info.getRoles().isEmpty());
    }

    @Test
    void settersUpdateFieldsCorrectly() {
        List<String> roles = List.of("MANAGER");

        AgentAuthenticationInfo info = new AgentAuthenticationInfo();
        info.setSubject("newSubject");
        info.setDocumentType(2);
        info.setDocumentNumber("98765432");
        info.setNames("Jane");
        info.setSurnames("Smith");
        info.setRoles(roles);

        assertEquals("newSubject", info.getSubject());
        assertEquals(2, info.getDocumentType());
        assertEquals("98765432", info.getDocumentNumber());
        assertEquals("Jane", info.getNames());
        assertEquals("Smith", info.getSurnames());
        assertEquals(roles, info.getRoles());
    }

    @Test
    void noArgsConstructorInitializesFieldsToNull() {
        AgentAuthenticationInfo info = new AgentAuthenticationInfo();

        assertNull(info.getSubject());
        assertNull(info.getDocumentType());
        assertNull(info.getDocumentNumber());
        assertNull(info.getNames());
        assertNull(info.getSurnames());
        assertNull(info.getRoles());
    }
}