package com.abcall.clientes.security;

import com.abcall.clientes.domain.dto.ClientAuthenticationInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void validateToken_ReturnsTrue_ForValidToken() {
        String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong";
        String token = Jwts.builder()
                .setSubject("1")
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        jwtTokenProvider.jwtSecret = secret;

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_ReturnsFalse_ForInvalidToken() {
        String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong";
        String invalidToken = "invalid.token.value";

        jwtTokenProvider.jwtSecret = secret;

        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void validateToken_ReturnsFalse_ForExpiredToken() {
        String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong";
        String expiredToken = Jwts.builder()
                .setSubject("1")
                .setExpiration(new java.util.Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        jwtTokenProvider.jwtSecret = secret;

        assertFalse(jwtTokenProvider.validateToken(expiredToken));
    }

    @Test
    void getAuthenticationInfoFromToken_ReturnsCorrectInfo_ForValidToken() {
        String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong";
        String token = Jwts.builder()
                .setSubject("1")
                .claim("username", "user123")
                .claim("roles", List.of("ROLE_USER", "ROLE_ADMIN"))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        jwtTokenProvider.jwtSecret = secret;

        ClientAuthenticationInfo info = jwtTokenProvider.getAuthenticationInfoFromToken(token);

        assertEquals(1L, info.getClientId());
        assertEquals("user123", info.getUsername());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), info.getRoles());
    }

    @Test
    void getAuthenticationInfoFromToken_ThrowsException_ForInvalidToken() {
        String secret = "aVerySecretKeyThatIsAtLeast32CharactersLong";
        String invalidToken = "invalid.token.value";

        jwtTokenProvider.jwtSecret = secret;

        assertThrows(Exception.class, () -> jwtTokenProvider.getAuthenticationInfoFromToken(invalidToken));
    }
}