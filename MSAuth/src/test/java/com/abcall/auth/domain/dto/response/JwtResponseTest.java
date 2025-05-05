package com.abcall.auth.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtResponseTest {

    @Test
    void shouldInitializeFieldsCorrectly() {
        JwtResponse jwtResponse = new JwtResponseImpl(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of("ROLE_USER", "ROLE_ADMIN"),
                "client"
        );

        assertEquals("sampleToken", jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals("sampleRefreshToken", jwtResponse.getRefreshToken());
        assertEquals(3600L, jwtResponse.getExpiresIn());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), jwtResponse.getRoles());
        assertEquals("client", jwtResponse.getUserType());
    }

    @Test
    void shouldHandleNullRoles() {
        JwtResponse jwtResponse = new JwtResponseImpl(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                null,
                "client"
        );

        assertNull(jwtResponse.getRoles());
    }

    @Test
    void shouldHandleEmptyRoles() {
        JwtResponse jwtResponse = new JwtResponseImpl(
                "sampleToken",
                "sampleRefreshToken",
                3600L,
                List.of(),
                "client"
        );

        assertTrue(jwtResponse.getRoles().isEmpty());
    }

    // Helper class to test the abstract JwtResponse
    private static class JwtResponseImpl extends JwtResponse {
        protected JwtResponseImpl(String token, String refreshToken, Long expiresIn, List<String> roles, String userType) {
            super(token, refreshToken, expiresIn, roles, userType);
        }
    }
}