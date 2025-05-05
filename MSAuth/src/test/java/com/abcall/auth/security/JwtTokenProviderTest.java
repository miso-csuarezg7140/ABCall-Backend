package com.abcall.auth.security;

import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.util.Constants;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtTokenProviderTest {

    @Test
    void shouldRefreshAgentTokensSuccessfully() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse(
                1,
                "12345678",
                List.of("ROLE_AGENT"),
                true,
                "John",
                "Doe"
        );
        String refreshToken = jwtTokenProvider.generateAgentRefreshToken(agentAuthResponse);

        TokenPair refreshedTokens = jwtTokenProvider.refreshTokens(refreshToken);

        assertNotNull(refreshedTokens.getAccessToken());
        assertNotNull(refreshedTokens.getRefreshToken());
    }

    @Test
    void shouldRefreshClientTokensSuccessfully() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        ClientAuthResponse clientAuthResponse = new ClientAuthResponse(
                123,
                "87654321",
                List.of("ROLE_CLIENT"),
                true,
                "Sample Company",
                "client@example.com"
        );
        String refreshToken = jwtTokenProvider.generateClientRefreshToken(clientAuthResponse);

        TokenPair refreshedTokens = jwtTokenProvider.refreshTokens(refreshToken);

        assertNotNull(refreshedTokens.getAccessToken());
        assertNotNull(refreshedTokens.getRefreshToken());
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse(
                1,
                "12345678",
                List.of("ROLE_AGENT"),
                true,
                "John",
                "Doe"
        );
        String token = jwtTokenProvider.generateAgentAccessToken(agentAuthResponse);

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void shouldReturnCorrectUserTypeFromToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse(
                1,
                "12345678",
                List.of("ROLE_AGENT"),
                true,
                "John",
                "Doe"
        );
        String token = jwtTokenProvider.generateAgentAccessToken(agentAuthResponse);

        assertEquals(Constants.AGENT, jwtTokenProvider.getUserTypeFromToken(token));
    }

    @Test
    void shouldReturnCorrectRolesFromToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse(
                1,
                "12345678",
                List.of("ROLE_AGENT"),
                true,
                "John",
                "Doe"
        );
        String token = jwtTokenProvider.generateAgentAccessToken(agentAuthResponse);

        List<String> roles = jwtTokenProvider.getRolesFromToken(token);

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ROLE_AGENT", roles.getFirst());
    }

    @Test
    void shouldGenerateAgentTokensSuccessfully() {
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse();
        agentAuthResponse.setDocumentType(1);
        agentAuthResponse.setDocumentNumber("12345678");
        agentAuthResponse.setNames("John");
        agentAuthResponse.setSurnames("Doe");
        agentAuthResponse.setRoles(List.of("ROLE_AGENT"));

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        TokenPair tokenPair = jwtTokenProvider.generateAgentTokens(agentAuthResponse);

        assertNotNull(tokenPair.getAccessToken());
        assertNotNull(tokenPair.getRefreshToken());
    }

    @Test
    void shouldGenerateClientTokensSuccessfully() {
        ClientAuthResponse clientAuthResponse = new ClientAuthResponse();
        clientAuthResponse.setClientId(123);
        clientAuthResponse.setDocumentNumber("87654321");
        clientAuthResponse.setSocialReason("Sample Company");
        clientAuthResponse.setEmail("client@example.com");
        clientAuthResponse.setRoles(List.of("ROLE_CLIENT"));

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);
        TokenPair tokenPair = jwtTokenProvider.generateClientTokens(clientAuthResponse);

        assertNotNull(tokenPair.getAccessToken());
        assertNotNull(tokenPair.getRefreshToken());
    }

    @Test
    void shouldReturnFalseForInvalidTokenValidation() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "c2VjcmV0S2V5VGhhdElzTG9uZ0Vub3VnaFRvQmVTZWN1cmVGb3JKV1RTaWduaW5n", 60000,
                120000);

        assertFalse(jwtTokenProvider.validateToken("invalidToken"));
    }
}