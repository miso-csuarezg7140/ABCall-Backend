package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JwtResponseTest {

    @Test
    void jwtResponseShouldStoreAllFields() {
        JwtResponse response = new JwtResponse("token", "Bearer", "refreshToken", 1,
                "username", "userType", List.of("ROLE_USER", "ROLE_ADMIN"));
        assertEquals("token", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals(1, response.getId());
        assertEquals("username", response.getUsername());
        assertEquals("userType", response.getUserType());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), response.getRoles());
    }

    @Test
    void jwtResponseShouldHandleEmptyRoles() {
        JwtResponse response = new JwtResponse("token", "Bearer", "refreshToken", 1,
                "username", "userType", List.of());
        assertEquals(List.of(), response.getRoles());
    }

    @Test
    void jwtResponseShouldHandleNullRoles() {
        JwtResponse response = new JwtResponse("token", "Bearer", "refreshToken", 1,
                "username", "userType", null);
        assertNull(response.getRoles());
    }

    @Test
    void jwtResponseShouldStoreTokenOnly() {
        JwtResponse response = new JwtResponse("token");
        assertEquals("token", response.getToken());
    }
}