package com.abcall.agentes.security;

import com.abcall.agentes.domain.dto.AgentAuthenticationInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final String jwtSecret = "aVerySecretKeyThatIsAtLeast32CharactersLong";

    @BeforeEach
    void setUp() {
        jwtTokenProvider.jwtSecret = jwtSecret;
    }

    @Test
    void validateToken_ReturnsTrue_WhenTokenIsValid() {
        String validToken = Jwts.builder()
                .setSubject("testUser")
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        boolean result = jwtTokenProvider.validateToken(validToken);

        assertTrue(result);
    }

    @Test
    void validateToken_ReturnsFalse_WhenTokenIsInvalid() {
        String invalidToken = "invalid.token.here";

        boolean result = jwtTokenProvider.validateToken(invalidToken);

        assertFalse(result);
    }

    @Test
    void validateToken_ReturnsFalse_WhenTokenIsExpired() {
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        boolean result = jwtTokenProvider.validateToken(expiredToken);

        assertFalse(result);
    }

    @Test
    void getAuthenticationInfoFromToken_ReturnsCorrectInfo_WhenTokenIsValid() {
        String validToken = Jwts.builder()
                .setSubject("testUser")
                .claim("documentType", "1")
                .claim("documentNumber", "123456")
                .claim("names", "John")
                .claim("surnames", "Doe")
                .claim("roles", List.of("ROLE_USER", "ROLE_ADMIN"))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        AgentAuthenticationInfo info = jwtTokenProvider.getAuthenticationInfoFromToken(validToken);

        assertNotNull(info);
        assertEquals("testUser", info.getSubject());
        assertEquals(1, info.getDocumentType());
        assertEquals("123456", info.getDocumentNumber());
        assertEquals("John", info.getNames());
        assertEquals("Doe", info.getSurnames());
        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), info.getRoles());
    }

    @Test
    void getAuthenticationInfoFromToken_ThrowsException_WhenTokenIsInvalid() {
        String invalidToken = "invalid.token.here";

        assertThrows(Exception.class, () ->
                jwtTokenProvider.getAuthenticationInfoFromToken(invalidToken)
        );
    }
}