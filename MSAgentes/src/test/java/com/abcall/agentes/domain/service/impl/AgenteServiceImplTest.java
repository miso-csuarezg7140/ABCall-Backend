package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.mappers.AgenteMapper;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import com.abcall.agentes.util.ApiUtils;
import com.abcall.agentes.util.enums.HttpResponseCodes;
import com.abcall.agentes.util.enums.HttpResponseMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Base64;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AgenteServiceImplTest {

    @Mock
    private AgenteRepository agenteRepository;

    @Mock
    private AgenteMapper agenteMapper;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private AgenteServiceImpl agenteService;

    private Agente agenteMock;
    private AgenteDto agenteDtoMock;
    private AgentePK agentePKMock;
    private ResponseServiceDto responseOk;
    private ResponseServiceDto responseUnauthorized;
    private ResponseServiceDto responseNoContent;
    private ResponseServiceDto responseError;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar datos de prueba
        agentePKMock = new AgentePK();
        agentePKMock.setTipoDocumento("CC");
        agentePKMock.setNumeroDocumento(123456789L);

        agenteMock = new Agente();
        agenteMock.setAgentePK(agentePKMock);
        // Otros campos del agente

        agenteDtoMock = new AgenteDto();
        agenteDtoMock.setTipoDocumento("CC");
        agenteDtoMock.setNumeroDocumento(123456789L);
        // Contraseña en Base64: "password"
        agenteDtoMock.setContrasena(Base64.getEncoder().encodeToString("password".getBytes()));

        // Configurar respuestas
        responseOk = new ResponseServiceDto();
        responseOk.setStatusCode(HttpResponseCodes.OK.getCode());
        responseOk.setStatusDescription(HttpResponseMessages.OK.getMessage());
        responseOk.setData(agenteDtoMock);

        responseUnauthorized = new ResponseServiceDto();
        responseUnauthorized.setStatusCode(HttpResponseCodes.UNAUTHORIZED.getCode());
        responseUnauthorized.setStatusDescription(HttpResponseMessages.UNAUTHORIZED.getMessage());
        responseUnauthorized.setData(new HashMap<>());

        responseNoContent = new ResponseServiceDto();
        responseNoContent.setStatusCode(HttpResponseCodes.BUSINESS_MISTAKE.getCode());
        responseNoContent.setStatusDescription(HttpResponseMessages.NO_CONTENT.getMessage());
        responseNoContent.setData(new HashMap<>());

        responseError = new ResponseServiceDto();
        responseError.setStatusCode(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode());
        responseError.setStatusDescription(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage());
        responseError.setData("Error message");
    }

    @Test
    public void loginSuccess() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agenteMock);
        when(agenteMapper.toDto(agenteMock)).thenReturn(agenteDtoMock);
        when(apiUtils.buildResponse(
                HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(),
                agenteDtoMock)).thenReturn(responseOk);

        // Act
        ResponseServiceDto result = agenteService.login("CC", "123456789", "password");

        // Assert
        assertEquals(HttpResponseCodes.OK.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.OK.getMessage(), result.getStatusDescription());
        assertSame(agenteDtoMock, result.getData());

        // Verify
        verify(agenteRepository).obtenerPorId(any(AgentePK.class));
        verify(agenteMapper).toDto(agenteMock);
        verify(apiUtils).buildResponse(
                HttpResponseCodes.OK.getCode(),
                HttpResponseMessages.OK.getMessage(),
                agenteDtoMock);
    }

    @Test
    public void loginWrongPassword() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agenteMock);
        when(agenteMapper.toDto(agenteMock)).thenReturn(agenteDtoMock);
        when(apiUtils.buildResponse(
                HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(),
                new HashMap<>())).thenReturn(responseUnauthorized);

        // Act
        ResponseServiceDto result = agenteService.login("CC", "123456789", "wrongPassword");

        // Assert
        assertEquals(HttpResponseCodes.UNAUTHORIZED.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.UNAUTHORIZED.getMessage(), result.getStatusDescription());

        // Verify
        verify(agenteRepository).obtenerPorId(any(AgentePK.class));
        verify(agenteMapper).toDto(agenteMock);
        verify(apiUtils).buildResponse(
                HttpResponseCodes.UNAUTHORIZED.getCode(),
                HttpResponseMessages.UNAUTHORIZED.getMessage(),
                new HashMap<>());
    }

    @Test
    public void loginUserNotFound() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agenteMock);
        when(agenteMapper.toDto(agenteMock)).thenReturn(null);
        when(apiUtils.buildResponse(
                HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(),
                new HashMap<>())).thenReturn(responseNoContent);

        // Act
        ResponseServiceDto result = agenteService.login("CC", "123456789", "password");

        // Assert
        assertEquals(HttpResponseCodes.BUSINESS_MISTAKE.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.NO_CONTENT.getMessage(), result.getStatusDescription());

        // Verify
        verify(agenteRepository).obtenerPorId(any(AgentePK.class));
        verify(agenteMapper).toDto(agenteMock);
        verify(apiUtils).buildResponse(
                HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                HttpResponseMessages.NO_CONTENT.getMessage(),
                new HashMap<>());
    }

    @Test
    public void loginExceptionThrown() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenThrow(new RuntimeException("Error message"));
        when(apiUtils.buildResponse(
                HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(),
                "Error message")).thenReturn(responseError);

        // Act
        ResponseServiceDto result = agenteService.login("CC", "123456789", "password");

        // Assert
        assertEquals(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), result.getStatusDescription());
        assertEquals("Error message", result.getData());

        // Verify
        verify(agenteRepository).obtenerPorId(any(AgentePK.class));
        verify(apiUtils).buildResponse(
                HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(),
                "Error message");
    }

    @Test
    public void loginInvalidDocumentNumber() {
        // Arrange
        when(apiUtils.buildResponse(
                HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(),
                "For input string: \"invalidNumber\"")).thenReturn(responseError);

        // Act
        ResponseServiceDto result = agenteService.login("CC", "invalidNumber", "password");

        // Assert
        assertEquals(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(), result.getStatusCode());
        assertEquals(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), result.getStatusDescription());

        // Verify
        verify(apiUtils).buildResponse(
                eq(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode()),
                eq(HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage()),
                any(String.class));
    }
}