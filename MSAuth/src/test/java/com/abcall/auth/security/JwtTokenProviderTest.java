package com.abcall.auth.security;

import com.abcall.auth.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        String jwtSecret = "mySecretKeymySecretKeymySecretKeymySecretKey";
        int jwtExpirationMs = 3600000;

        // Don't mock the key - use a real key instead
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        // Create the provider with the same secret
        jwtTokenProvider = new JwtTokenProvider(jwtSecret, jwtExpirationMs);
    }

    @Test
    void generateAgentTokenShouldReturnValidToken() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));

        // Use the same key for validation that the provider used
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("agent", claims.getSubject());
        assertEquals("agent", claims.get(Constants.USER_TYPE));
        assertEquals(1, claims.get("agentId"));
        assertEquals(List.of("ROLE_USER"), claims.get(Constants.ROLES));
    }

    @Test
    void generateClientTokenShouldReturnValidToken() {
        String token = jwtTokenProvider.generateClientToken(1, "client", List.of("ROLE_USER"));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("client", claims.getSubject());
        assertEquals("client", claims.get(Constants.USER_TYPE));
        assertEquals(1, claims.get("clientId"));
        assertEquals(List.of("ROLE_USER"), claims.get(Constants.ROLES));
    }

    @Test
    void validateTokenShouldReturnTrueForValidToken() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateTokenShouldReturnFalseForInvalidToken() {
        String token = "invalidToken";
        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    void getClaimsFromTokenShouldReturnClaims() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        Claims claims = jwtTokenProvider.getClaimsFromToken(token);
        assertEquals("agent", claims.getSubject());
    }

    @Test
    void getUsernameFromTokenShouldReturnUsername() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertEquals("agent", jwtTokenProvider.getUsernameFromToken(token));
    }

    @Test
    void getUserTypeFromTokenShouldReturnUserType() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertEquals("agent", jwtTokenProvider.getUserTypeFromToken(token));
    }

    @Test
    void isTokenExpiredShouldReturnTrueForExpiredToken() {
        // Use the same key generation as in the provider
        String token = Jwts.builder()
                .setSubject("agent")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(secretKey)
                .compact();

        assertTrue(jwtTokenProvider.isTokenExpired(token));
    }

    @Test
    void isTokenExpiredShouldReturnFalseForValidToken() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertFalse(jwtTokenProvider.isTokenExpired(token));
    }

    @Test
    void refreshTokenShouldReturnNewToken() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        String refreshedToken = jwtTokenProvider.refreshToken(token);
        Claims claims = jwtTokenProvider.getClaimsFromToken(refreshedToken);
        assertEquals("agent", claims.getSubject());
    }

    @Test
    void getUserIdFromTokenShouldReturnAgentId() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertEquals(1, jwtTokenProvider.getUserIdFromToken(token));
    }

    @Test
    void getUserIdFromTokenShouldReturnClientId() {
        String token = jwtTokenProvider.generateClientToken(1, "client", List.of("ROLE_USER"));
        assertEquals(1, jwtTokenProvider.getUserIdFromToken(token));
    }

    @Test
    void getRolesFromTokenShouldReturnRoles() {
        String token = jwtTokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"));
        assertEquals(List.of("ROLE_USER"), jwtTokenProvider.getRolesFromToken(token));
    }
}