package com.abcall.clientes.security;

import com.abcall.clientes.domain.dto.ClientAuthenticationInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final String jwtSecret = "thisIsATestSecretKeyForJwtTokenTesting123456";

    @BeforeEach
    void setUp() {
        jwtTokenProvider.jwtSecret = jwtSecret;
    }

    @Test
    void validateToken_ReturnsTrue_WhenTokenIsValid() {
        String validToken = Jwts.builder()
                .setSubject("testSubject")
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        boolean result = jwtTokenProvider.validateToken(validToken);

        assertTrue(result);
    }

    @Test
    void validateToken_ReturnsFalse_WhenTokenIsInvalid() {
        String invalidToken = "invalidToken";

        boolean result = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(result);
    }

    @Test
    void validateToken_ReturnsFalse_WhenTokenIsExpired() {
        String expiredToken = Jwts.builder()
                .setSubject("testSubject")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        boolean result = jwtTokenProvider.validateToken(expiredToken);

        assertFalse(result);
    }

    @Test
    void getAuthenticationInfoFromToken_ReturnsCorrectInfo_WhenTokenIsValid() {
        String validToken = Jwts.builder()
                .setSubject("testSubject")
                .claim("clientId", 123)
                .claim("documentNumber", 456789L)
                .claim("socialReason", "Test Social Reason")
                .claim("email", "test@example.com")
                .claim("roles", List.of("ROLE_USER", "ROLE_ADMIN"))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        ClientAuthenticationInfo info = jwtTokenProvider.getAuthenticationInfoFromToken(validToken);

        assertNotNull(info);
        assertEquals("testSubject", info.getSubject());
        assertEquals(123, info.getClientId());
        assertEquals(456789L, info.getDocumentNumber());
        assertEquals("Test Social Reason", info.getSocialReason());
        assertEquals("test@example.com", info.getEmail());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), info.getRoles());
    }
}