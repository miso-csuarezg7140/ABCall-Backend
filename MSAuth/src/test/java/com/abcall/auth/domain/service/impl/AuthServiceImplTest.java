package com.abcall.auth.domain.service.impl;

import com.abcall.auth.domain.dto.request.AgentAuthRequest;
import com.abcall.auth.domain.dto.request.ClientAuthRequest;
import com.abcall.auth.domain.dto.request.LoginRequest;
import com.abcall.auth.domain.dto.request.TokenRefreshRequest;
import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.AgentJwtResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.domain.dto.response.ClientJwtResponse;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.security.JwtTokenProvider;
import com.abcall.auth.security.TokenPair;
import com.abcall.auth.util.ApiUtils;
import com.abcall.auth.util.enums.HttpResponseCodes;
import com.abcall.auth.util.enums.HttpResponseMessages;
import com.abcall.auth.web.external.IAgentService;
import com.abcall.auth.web.external.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private IAgentService agentService;

    @Mock
    private IClientService clientService;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private AuthServiceImpl authService;

    private ObjectMapper objectMapper;
    private LoginRequest agentLoginRequest;
    private LoginRequest clientLoginRequest;
    private AgentAuthResponse agentAuthResponse;
    private ClientAuthResponse clientAuthResponse;
    private ResponseServiceDto agentServiceResponse;
    private ResponseServiceDto clientServiceResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Crear request de login para agente
        agentLoginRequest = new LoginRequest();
        agentLoginRequest.setUserType("agent");
        agentLoginRequest.setDocumentType("1");
        agentLoginRequest.setDocumentNumber("12345678");
        agentLoginRequest.setPassword("password123");

        // Crear request de login para cliente
        clientLoginRequest = new LoginRequest();
        clientLoginRequest.setUserType("client");
        clientLoginRequest.setDocumentNumber("87654321");
        clientLoginRequest.setPassword("password456");

        // Crear respuesta autenticación de agente
        agentAuthResponse = new AgentAuthResponse();
        agentAuthResponse.setAuthenticated(true);
        agentAuthResponse.setDocumentNumber("12345678");
        agentAuthResponse.setDocumentType(1);
        agentAuthResponse.setRoles(Collections.singletonList("AGENT"));
        agentAuthResponse.setNames("John");
        agentAuthResponse.setSurnames("Doe");

        // Crear respuesta autenticación de cliente
        clientAuthResponse = new ClientAuthResponse();
        clientAuthResponse.setAuthenticated(true);
        clientAuthResponse.setClientId(1);
        clientAuthResponse.setDocumentNumber("87654321");
        clientAuthResponse.setRoles(Collections.singletonList("CLIENT"));
        clientAuthResponse.setSocialReason("ACME Corp");
        clientAuthResponse.setEmail("contact@acme.com");

        // Crear respuesta del servicio de agente
        agentServiceResponse = new ResponseServiceDto();
        agentServiceResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        agentServiceResponse.setData(agentAuthResponse);

        // Crear respuesta del servicio de cliente
        clientServiceResponse = new ResponseServiceDto();
        clientServiceResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        clientServiceResponse.setData(clientAuthResponse);
    }

    @Test
    void authenticateUser_WithValidAgentCredentials_ReturnsJwtToken() {
        // Arrange
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(agentServiceResponse);
        TokenPair tokenPair = new TokenPair("access.token.here", "refresh.token.here");

        when(agentService.authenticateAgent(any(AgentAuthRequest.class))).thenReturn(responseEntity);
        when(tokenProvider.generateAgentTokens(any(AgentAuthResponse.class))).thenReturn(tokenPair);
        when(tokenProvider.getTokenExpirationInSeconds(anyString())).thenReturn(3600L);

        ResponseServiceDto expectedResponse = getResponseServiceDto(tokenPair);

        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(AgentJwtResponse.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(agentLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        verify(agentService).authenticateAgent(any(AgentAuthRequest.class));
        verify(tokenProvider).generateAgentTokens(any(AgentAuthResponse.class));
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()), eq(HttpResponseMessages.OK.getMessage()), any(AgentJwtResponse.class));
    }

    private ResponseServiceDto getResponseServiceDto(TokenPair tokenPair) {
        AgentJwtResponse expectedJwtResponse = new AgentJwtResponse(
                tokenPair.getAccessToken(),
                tokenPair.getRefreshToken(),
                3600L,
                agentAuthResponse.getRoles(),
                agentAuthResponse.getDocumentNumber(),
                agentAuthResponse.getDocumentType(),
                agentAuthResponse.getNames(),
                agentAuthResponse.getSurnames()
        );

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.OK.getMessage());
        expectedResponse.setData(expectedJwtResponse);
        return expectedResponse;
    }

    @Test
    void authenticateUser_WithValidClientCredentials_ReturnsJwtToken() {
        // Arrange
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(clientServiceResponse);
        TokenPair tokenPair = new TokenPair("access.token.client", "refresh.token.client");

        when(clientService.authenticateClient(any(ClientAuthRequest.class))).thenReturn(responseEntity);
        when(tokenProvider.generateClientTokens(any(ClientAuthResponse.class))).thenReturn(tokenPair);
        when(tokenProvider.getTokenExpirationInSeconds(anyString())).thenReturn(3600L);

        ResponseServiceDto expectedResponse = getServiceDto(tokenPair);

        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(ClientJwtResponse.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(clientLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        verify(clientService).authenticateClient(any(ClientAuthRequest.class));
        verify(tokenProvider).generateClientTokens(any(ClientAuthResponse.class));
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()), eq(HttpResponseMessages.OK.getMessage()), any(ClientJwtResponse.class));
    }

    private ResponseServiceDto getServiceDto(TokenPair tokenPair) {
        ClientJwtResponse expectedJwtResponse = new ClientJwtResponse(
                tokenPair.getAccessToken(),
                tokenPair.getRefreshToken(),
                3600L,
                clientAuthResponse.getRoles(),
                clientAuthResponse.getClientId(),
                clientAuthResponse.getDocumentNumber(),
                clientAuthResponse.getSocialReason(),
                clientAuthResponse.getEmail()
        );

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.OK.getMessage());
        expectedResponse.setData(expectedJwtResponse);
        return expectedResponse;
    }

    @Test
    void authenticateUser_WithInvalidAgentCredentials_ReturnsUnauthorized() {
        // Arrange
        AgentAuthResponse invalidAgentResponse = new AgentAuthResponse();
        invalidAgentResponse.setAuthenticated(false);

        ResponseServiceDto invalidServiceResponse = new ResponseServiceDto();
        invalidServiceResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        invalidServiceResponse.setData(invalidAgentResponse);

        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(invalidServiceResponse);

        when(agentService.authenticateAgent(any(AgentAuthRequest.class))).thenReturn(responseEntity);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        expectedResponse.setData(new HashMap<>());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(agentLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(agentService).authenticateAgent(any(AgentAuthRequest.class));
        verify(tokenProvider, never()).generateAgentTokens(any());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()), eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class));
    }

    @Test
    void authenticateUser_WithInvalidClientCredentials_ReturnsUnauthorized() {
        // Arrange
        ClientAuthResponse invalidClientResponse = new ClientAuthResponse();
        invalidClientResponse.setAuthenticated(false);

        ResponseServiceDto invalidServiceResponse = new ResponseServiceDto();
        invalidServiceResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        invalidServiceResponse.setData(invalidClientResponse);

        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(invalidServiceResponse);

        when(clientService.authenticateClient(any(ClientAuthRequest.class))).thenReturn(responseEntity);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        expectedResponse.setData(new HashMap<>());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(clientLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(clientService).authenticateClient(any(ClientAuthRequest.class));
        verify(tokenProvider, never()).generateClientTokens(any());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()), eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class));
    }

    @Test
    void authenticateUser_WithNullResponseBody_ReturnsUnauthorized() {
        // Arrange
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(null);

        when(agentService.authenticateAgent(any(AgentAuthRequest.class))).thenReturn(responseEntity);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        expectedResponse.setData(new HashMap<>());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(agentLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(agentService).authenticateAgent(any(AgentAuthRequest.class));
        verify(tokenProvider, never()).generateAgentTokens(any());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()), eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class));
    }

    @Test
    void authenticateUser_WithNonOkStatusCode_ReturnsUnauthorized() {
        // Arrange
        ResponseServiceDto badRequestResponse = new ResponseServiceDto();
        badRequestResponse.setStatusCode(HttpResponseCodes.BAD_REQUEST.getCode());
        badRequestResponse.setData(null);

        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(badRequestResponse);

        when(clientService.authenticateClient(any(ClientAuthRequest.class))).thenReturn(responseEntity);

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        expectedResponse.setData(new HashMap<>());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()),
                eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(clientLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(clientService).authenticateClient(any(ClientAuthRequest.class));
        verify(tokenProvider, never()).generateClientTokens(any());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.UNAUTHORIZED.getCode()), eq(HttpResponseMessages.UNAUTHORIZED.getMessage()), any(HashMap.class));
    }

    @Test
    void authenticateUser_ThrowsException_ReturnsInternalServerError() {
        // Arrange
        when(agentService.authenticateAgent(any(AgentAuthRequest.class))).thenThrow(new RuntimeException("Test exception"));

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage());
        expectedResponse.setData("Test exception");

        when(apiUtils.buildResponse(
                HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(),
                "Test exception"
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.authenticateUser(agentLoginRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(), result.getStatusCode());
        verify(apiUtils).buildResponse(
                HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(),
                "Test exception"
        );
    }

    @Test
    void refreshToken_WithValidAgentToken_ReturnsNewTokens() {
        // Arrange
        TokenRefreshRequest refreshRequest = new TokenRefreshRequest("valid.refresh.token");
        TokenPair tokenPair = new TokenPair("new.access.token", "new.refresh.token");

        when(tokenProvider.refreshTokens(anyString())).thenReturn(tokenPair);
        when(tokenProvider.getUserTypeFromToken(anyString())).thenReturn("agent");
        when(tokenProvider.getTokenExpirationInSeconds(anyString())).thenReturn(3600L);

        Claims claims = new DefaultClaims();
        claims.put("documentNumber", "12345678");
        claims.put("documentType", 1);
        claims.put("names", "John");
        claims.put("surnames", "Doe");

        List<String> roles = Arrays.asList("AGENT", "ADMIN");

        when(tokenProvider.getClaimsFromToken(anyString())).thenReturn(claims);
        when(tokenProvider.getRolesFromToken(anyString())).thenReturn(roles);

        ResponseServiceDto expectedResponse = getResponseServiceDto(tokenPair, roles);

        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(AgentJwtResponse.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        verify(tokenProvider).refreshTokens(anyString());
        verify(tokenProvider).getUserTypeFromToken(anyString());
        verify(tokenProvider).getClaimsFromToken(anyString());
        verify(tokenProvider).getRolesFromToken(anyString());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(AgentJwtResponse.class));
    }

    private static ResponseServiceDto getResponseServiceDto(TokenPair tokenPair, List<String> roles) {
        AgentJwtResponse expectedJwtResponse = new AgentJwtResponse(
                tokenPair.getAccessToken(),
                tokenPair.getRefreshToken(),
                3600L,
                roles,
                "12345678",
                1,
                "John",
                "Doe"
        );

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.OK.getMessage());
        expectedResponse.setData(expectedJwtResponse);
        return expectedResponse;
    }

    @Test
    void refreshToken_WithValidClientToken_ReturnsNewTokens() {
        // Arrange
        TokenRefreshRequest refreshRequest = new TokenRefreshRequest("valid.refresh.token");
        TokenPair tokenPair = new TokenPair("new.access.token", "new.refresh.token");

        when(tokenProvider.refreshTokens(anyString())).thenReturn(tokenPair);
        when(tokenProvider.getUserTypeFromToken(anyString())).thenReturn("client");
        when(tokenProvider.getTokenExpirationInSeconds(anyString())).thenReturn(3600L);

        Claims claims = new DefaultClaims();
        claims.put("clientId", 1);
        claims.put("documentNumber", "87654321");
        claims.put("socialReason", "ACME Corp");
        claims.put("email", "contact@acme.com");

        List<String> roles = Collections.singletonList("CLIENT");

        when(tokenProvider.getClaimsFromToken(anyString())).thenReturn(claims);
        when(tokenProvider.getRolesFromToken(anyString())).thenReturn(roles);

        ResponseServiceDto expectedResponse = getServiceDto(tokenPair, roles);

        when(apiUtils.buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(ClientJwtResponse.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        verify(tokenProvider).refreshTokens(anyString());
        verify(tokenProvider).getUserTypeFromToken(anyString());
        verify(tokenProvider).getClaimsFromToken(anyString());
        verify(tokenProvider).getRolesFromToken(anyString());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()),
                eq(HttpResponseMessages.OK.getMessage()), any(ClientJwtResponse.class));
    }

    private static ResponseServiceDto getServiceDto(TokenPair tokenPair, List<String> roles) {
        ClientJwtResponse expectedJwtResponse = new ClientJwtResponse(
                tokenPair.getAccessToken(),
                tokenPair.getRefreshToken(),
                3600L,
                roles,
                1,
                "87654321",
                "ACME Corp",
                "contact@acme.com"
        );

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.OK.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.OK.getMessage());
        expectedResponse.setData(expectedJwtResponse);
        return expectedResponse;
    }

    @Test
    void refreshToken_WithInvalidUserType_ReturnsBadRequest() {
        // Arrange
        TokenRefreshRequest refreshRequest = new TokenRefreshRequest("valid.refresh.token");
        TokenPair tokenPair = new TokenPair("new.access.token", "new.refresh.token");

        when(tokenProvider.refreshTokens(anyString())).thenReturn(tokenPair);
        when(tokenProvider.getUserTypeFromToken(anyString())).thenReturn("invalid");

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.BAD_REQUEST.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.BAD_REQUEST.getMessage());
        expectedResponse.setData(new HashMap<>());

        when(apiUtils.buildResponse(eq(HttpResponseCodes.BAD_REQUEST.getCode()),
                eq(HttpResponseMessages.BAD_REQUEST.getMessage()), any(HashMap.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.BAD_REQUEST.getCode(), result.getStatusCode());
        verify(tokenProvider).refreshTokens(anyString());
        verify(tokenProvider).getUserTypeFromToken(anyString());
        verify(tokenProvider, never()).getClaimsFromToken(anyString());
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.BAD_REQUEST.getCode()), eq(HttpResponseMessages.BAD_REQUEST.getMessage()), any(HashMap.class));
    }

    @Test
    void refreshToken_ThrowsException_ReturnsUnauthorized() {
        // Arrange
        TokenRefreshRequest refreshRequest = new TokenRefreshRequest("invalid.refresh.token");

        when(tokenProvider.refreshTokens(anyString())).thenThrow(new RuntimeException("Token expired"));

        ResponseServiceDto expectedResponse = new ResponseServiceDto();
        expectedResponse.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        expectedResponse.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        expectedResponse.setData("Token expired");

        when(apiUtils.buildResponse(
                HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(),
                "Token expired"
        )).thenReturn(expectedResponse);

        // Act
        ResponseServiceDto result = authService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        verify(tokenProvider).refreshTokens(anyString());
        verify(apiUtils).buildResponse(
                HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(),
                "Token expired"
        );
    }
}