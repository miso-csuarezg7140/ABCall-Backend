package com.abcall.agentes.domain.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AgentAuthResponseTest {

    @Test
    void allArgsConstructorInitializesAllFieldsCorrectly() {
        List<String> roles = List.of("ADMIN", "USER");

        AgentAuthResponse response = new AgentAuthResponse(1, "12345678", roles, true, "John", "Doe");

        assertEquals(1, response.getDocumentType());
        assertEquals("12345678", response.getDocumentNumber());
        assertEquals(roles, response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("John", response.getNames());
        assertEquals("Doe", response.getSurnames());
    }

    @Test
    void noArgsConstructorInitializesFieldsToDefaultValues() {
        AgentAuthResponse response = new AgentAuthResponse();

        assertNull(response.getDocumentType());
        assertNull(response.getDocumentNumber());
        assertNull(response.getRoles());
        assertFalse(response.isAuthenticated());
        assertNull(response.getNames());
        assertNull(response.getSurnames());
    }

    @Test
    void settersUpdateFieldsCorrectly() {
        List<String> roles = List.of("ADMIN", "USER");

        AgentAuthResponse response = new AgentAuthResponse();
        response.setDocumentType(2);
        response.setDocumentNumber("98765432");
        response.setRoles(roles);
        response.setAuthenticated(false);
        response.setNames("Jane");
        response.setSurnames("Smith");

        assertEquals(2, response.getDocumentType());
        assertEquals("98765432", response.getDocumentNumber());
        assertEquals(roles, response.getRoles());
        assertFalse(response.isAuthenticated());
        assertEquals("Jane", response.getNames());
        assertEquals("Smith", response.getSurnames());
    }

    @Test
    void builderCreatesObjectWithSpecifiedValues() {
        List<String> roles = List.of("ADMIN");

        AgentAuthResponse response = AgentAuthResponse.builder()
                .documentType(3)
                .documentNumber("11223344")
                .roles(roles)
                .authenticated(true)
                .names("Alice")
                .surnames("Johnson")
                .build();

        assertEquals(3, response.getDocumentType());
        assertEquals("11223344", response.getDocumentNumber());
        assertEquals(roles, response.getRoles());
        assertTrue(response.isAuthenticated());
        assertEquals("Alice", response.getNames());
        assertEquals("Johnson", response.getSurnames());
    }
}