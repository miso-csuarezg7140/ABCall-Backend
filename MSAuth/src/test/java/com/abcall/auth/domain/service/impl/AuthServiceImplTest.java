package com.abcall.auth.domain.service.impl;

import com.abcall.auth.domain.dto.request.AgentAuthRequest;
import com.abcall.auth.domain.dto.request.ClientAuthRequest;
import com.abcall.auth.domain.dto.response.AgentAuthResponse;
import com.abcall.auth.domain.dto.response.ClientAuthResponse;
import com.abcall.auth.domain.dto.response.JwtResponse;
import com.abcall.auth.domain.dto.response.ResponseServiceDto;
import com.abcall.auth.security.JwtTokenProvider;
import com.abcall.auth.util.ApiUtils;
import com.abcall.auth.util.enums.HttpResponseCodes;
import com.abcall.auth.util.enums.HttpResponseMessages;
import com.abcall.auth.web.external.IAgentService;
import com.abcall.auth.web.external.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUserShouldReturnJwtResponseForAgent() {
        AgentAuthRequest request = new AgentAuthRequest("agent", "password");
        AgentAuthResponse agentResponse = new AgentAuthResponse(1, "agent", List.of("ROLE_USER"), true);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(), agentResponse);
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(responseServiceDto);
        when(agentService.authenticateAgent(request)).thenReturn(responseEntity);
        when(tokenProvider.generateAgentToken(1, "agent", List.of("ROLE_USER"))).thenReturn("token");
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(), new JwtResponse("token"))).thenReturn(responseServiceDto);

        ResponseServiceDto result = authService.authenticateUser("agent", "password", "agent");

        assertEquals(responseServiceDto, result);
    }

    @Test
    void authenticateUserShouldReturnJwtResponseForClient() {
        ClientAuthRequest request = new ClientAuthRequest("client", "password");
        ClientAuthResponse clientResponse = new ClientAuthResponse(1, "client", List.of("ROLE_USER"), true, "Company", "client@example.com", null);
        ResponseServiceDto responseServiceDto = new ResponseServiceDto(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(), clientResponse);
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(responseServiceDto);
        when(clientService.authenticateClient(request)).thenReturn(responseEntity);
        when(tokenProvider.generateClientToken(1, "client", List.of("ROLE_USER"))).thenReturn("token");
        when(apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(), new JwtResponse("token"))).thenReturn(responseServiceDto);

        ResponseServiceDto result = authService.authenticateUser("client", "password", "client");

        assertEquals(responseServiceDto, result);
    }

    @Test
    void authenticateUserShouldReturnUnauthorizedForInvalidAgentCredentials() {
        AgentAuthRequest request = new AgentAuthRequest("agent", "wrongpassword");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto(HttpResponseCodes.UNAUTHORIZED.getCode(), HttpResponseMessages.UNAUTHORIZED.getMessage(), "Credenciales inválidas");
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(responseServiceDto);
        when(agentService.authenticateAgent(request)).thenReturn(responseEntity);
        when(apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(), HttpResponseMessages.UNAUTHORIZED.getMessage(), "Credenciales inválidas")).thenReturn(responseServiceDto);

        ResponseServiceDto result = authService.authenticateUser("agent", "wrongpassword", "agent");

        assertEquals(responseServiceDto, result);
    }

    @Test
    void authenticateUserShouldReturnUnauthorizedForInvalidClientCredentials() {
        ClientAuthRequest request = new ClientAuthRequest("client", "wrongpassword");
        ResponseServiceDto responseServiceDto = new ResponseServiceDto(HttpResponseCodes.UNAUTHORIZED.getCode(), HttpResponseMessages.UNAUTHORIZED.getMessage(), "Credenciales inválidas");
        ResponseEntity<ResponseServiceDto> responseEntity = ResponseEntity.ok(responseServiceDto);
        when(clientService.authenticateClient(request)).thenReturn(responseEntity);
        when(apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(), HttpResponseMessages.UNAUTHORIZED.getMessage(), "Credenciales inválidas")).thenReturn(responseServiceDto);

        ResponseServiceDto result = authService.authenticateUser("client", "wrongpassword", "client");

        assertEquals(responseServiceDto, result);
    }
}