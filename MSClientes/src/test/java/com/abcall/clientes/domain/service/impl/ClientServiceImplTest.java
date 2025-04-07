package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.config.SecurityConfig;
import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ClientAuthResponse;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.persistence.repository.IClienteRepository;
import com.abcall.clientes.util.ApiUtils;
import com.abcall.clientes.util.enums.HttpResponseCodes;
import com.abcall.clientes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private IClienteRepository clientRepository;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    void authenticateClient_ReturnsOkResponse_WhenCredentialsAreValid() {
        String username = "123";
        String password = "password";
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("encodedPassword");

        when(clientRepository.findByDocumentNumber(Long.parseLong(username))).thenReturn(clientDto);
        when(passwordEncoder.matches(password, clientDto.getPassword())).thenReturn(true);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.authenticateClient(username, password);

        assertNotNull(response);
        verify(clientRepository).save(clientDto);
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.OK.getCode()), eq(HttpResponseMessages.OK.getMessage()),
                any(ClientAuthResponse.class));
    }

    @Test
    void authenticateClient_ReturnsNoContentResponse_WhenClientDoesNotExist() {
        String username = "123";
        String password = "password";

        when(clientRepository.findByDocumentNumber(Long.parseLong(username))).thenReturn(null);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.authenticateClient(username, password);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
    }

    @Test
    void authenticateClient_ReturnsUnauthorizedResponse_WhenPasswordIsInvalid() {
        String username = "123";
        String password = "password";
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("encodedPassword");

        when(clientRepository.findByDocumentNumber(Long.parseLong(username))).thenReturn(clientDto);
        when(passwordEncoder.matches(password, clientDto.getPassword())).thenReturn(false);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.authenticateClient(username, password);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());
    }

    @Test
    void authenticateClient_ReturnsInternalServerErrorResponse_WhenExceptionIsThrown() {
        String username = "123";
        String password = "password";

        when(clientRepository.findByDocumentNumber(Long.parseLong(username))).thenThrow(
                new RuntimeException("Database error"));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.authenticateClient(username, password);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), "Database error");
    }
}