package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ClientAuthResponse;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;
import com.abcall.clientes.persistence.repository.IClientRepository;
import com.abcall.clientes.security.EncoderConfig;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private EncoderConfig encoderConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(encoderConfig.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    void authenticateClient_ReturnsOkResponse_WhenCredentialsAreValid() {
        String username = "123";
        String password = "password";
        ClientDto clientDto = new ClientDto();
        clientDto.setPassword("password");

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

    @Test
    void registerClient_ReturnsCreatedResponse_WhenClientIsSuccessfullyRegistered() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        when(clientRepository.findByDocumentNumber(Long.parseLong(request.getDocumentNumber()))).thenReturn(null);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(clientRepository.save(any(ClientDto.class))).thenAnswer(invocation -> {
            ClientDto client = invocation.getArgument(0);
            client.setIdClient(1);
            return client;
        });
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.registerClient(request);

        assertNotNull(response);
        verify(clientRepository).save(any(ClientDto.class));
        verify(apiUtils).buildResponse(eq(HttpResponseCodes.CREATED.getCode()),
                eq(HttpResponseMessages.CREATED.getMessage()), any(Map.class));
    }

    @Test
    void registerClient_ReturnsBusinessMistakeResponse_WhenClientAlreadyExists() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        when(clientRepository.findByDocumentNumber(Long.parseLong(request.getDocumentNumber()))).thenReturn(new ClientDto());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.registerClient(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.BUSINESS_MISTAKE.getMessage(), new HashMap<>());
    }

    @Test
    void registerClient_ReturnsInternalServerErrorResponse_WhenExceptionIsThrown() {
        ClientRegisterRequest request = ClientRegisterRequest.builder()
                .documentNumber("123456789")
                .socialReason("ABC Corp")
                .email("abc@corp.com")
                .password("password123")
                .build();

        when(clientRepository.findByDocumentNumber(Long.parseLong(request.getDocumentNumber()))).thenThrow(
                new RuntimeException("Database error"));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.registerClient(request);

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), "Database error");
    }

    @Test
    void listarClientes_ReturnsOkResponse_WhenClientsAreFound() {
        List<ListClientResponse> clientDtoList = List.of(new ListClientResponse(), new ListClientResponse());

        when(clientRepository.findActiveClients()).thenReturn(clientDtoList);
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.listarClientes();

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(), clientDtoList);
    }

    @Test
    void listarClientes_ReturnsNoContentResponse_WhenNoClientsAreFound() {
        when(clientRepository.findActiveClients()).thenReturn(List.of());
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.listarClientes();

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.NO_CONTENT.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
    }

    @Test
    void listarClientes_ReturnsInternalServerErrorResponse_WhenExceptionIsThrown() {
        when(clientRepository.findActiveClients()).thenThrow(new RuntimeException("Database error"));
        when(apiUtils.buildResponse(anyInt(), anyString(), any())).thenReturn(new ResponseServiceDto());

        ResponseServiceDto response = clientServiceImpl.listarClientes();

        assertNotNull(response);
        verify(apiUtils).buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), "Database error");
    }
}