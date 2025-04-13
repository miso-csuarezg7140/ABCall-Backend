package com.abcall.clientes.domain.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAuthenticationInfoTest {

    @Test
    void clientAuthenticationInfo_HasCorrectFieldValues() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .clientId(1L)
                .username("user123")
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build();

        assertEquals(1L, info.getClientId());
        assertEquals("user123", info.getUsername());
        assertEquals(Arrays.asList("ROLE_USER", "ROLE_ADMIN"), info.getRoles());
    }

    @Test
    void clientAuthenticationInfo_AllowsNullValuesForOptionalFields() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .clientId(1L)
                .username(null)
                .roles(null)
                .build();

        assertEquals(1L, info.getClientId());
        assertNull(info.getUsername());
        assertNull(info.getRoles());
    }

    @Test
    void clientAuthenticationInfo_AllowsEmptyRolesList() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .clientId(1L)
                .username("user123")
                .roles(Collections.emptyList())
                .build();

        assertEquals(1L, info.getClientId());
        assertEquals("user123", info.getUsername());
        assertTrue(info.getRoles().isEmpty());
    }

    @Test
    void settersUpdateFieldsCorrectly() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .clientId(1L)
                .username("user123")
                .roles(List.of("ROLE_USER"))
                .build();

        info.setClientId(2L);
        info.setUsername("newUser");
        info.setRoles(List.of("ROLE_ADMIN"));

        assertEquals(2L, info.getClientId());
        assertEquals("newUser", info.getUsername());
        assertEquals(List.of("ROLE_ADMIN"), info.getRoles());
    }

    @Test
    void settersAllowNullValues() {
        ClientAuthenticationInfo info = ClientAuthenticationInfo.builder()
                .clientId(1L)
                .username("user123")
                .roles(List.of("ROLE_USER"))
                .build();

        info.setClientId(null);
        info.setUsername(null);
        info.setRoles(null);

        assertNull(info.getClientId());
        assertNull(info.getUsername());
        assertNull(info.getRoles());
    }
}