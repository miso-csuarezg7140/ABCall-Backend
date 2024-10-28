package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.ResponseServiceDto;
import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.mappers.AgenteMapper;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.abcall.agentes.util.Constant.CODIGO_200;
import static com.abcall.agentes.util.Constant.CODIGO_206;
import static com.abcall.agentes.util.Constant.CODIGO_401;
import static com.abcall.agentes.util.Constant.CODIGO_500;
import static com.abcall.agentes.util.Constant.MENSAJE_200;
import static com.abcall.agentes.util.Constant.MENSAJE_206;
import static com.abcall.agentes.util.Constant.MENSAJE_401;
import static com.abcall.agentes.util.Constant.MENSAJE_500;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgenteServiceImplTest {

    @Mock
    private AgenteRepository agenteRepository;

    @Mock
    private AgenteMapper agenteMapper;

    @InjectMocks
    private AgenteServiceImpl agenteService;

    private Agente agente;
    private AgenteDto agenteDto;
    private AgentePK agentePK;
    private static final String TIPO_DOCUMENTO = "CC";
    private static final String NUM_DOCUMENTO = "123456789";
    private static final String CONTRASENA = "password123";
    private static final String CONTRASENA_ENCODED = "cGFzc3dvcmQxMjM="; // "password123" en Base64

    @BeforeEach
    void setUp() {
        // Configurar AgentePK
        agentePK = new AgentePK();
        agentePK.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK.setNumeroDocumento(Long.valueOf(NUM_DOCUMENTO));

        // Configurar Agente
        agente = new Agente();
        agente.setAgentePK(agentePK);
        agente.setContrasena(CONTRASENA_ENCODED);

        // Configurar AgenteDto
        agenteDto = new AgenteDto();
        agenteDto.setTipoDocumento(TIPO_DOCUMENTO);
        agenteDto.setNumeroDocumento(Long.valueOf(NUM_DOCUMENTO));
        agenteDto.setContrasena(CONTRASENA_ENCODED);
    }

    @Test
    void loginExitoso() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agente);
        when(agenteMapper.toDto(agente)).thenReturn(agenteDto);

        // Act
        ResponseServiceDto response = agenteService.login(TIPO_DOCUMENTO, NUM_DOCUMENTO, CONTRASENA);

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_200, response.getStatusCode());
        assertEquals(MENSAJE_200, response.getStatusDescription());
        assertNotNull(response.getData());
        verify(agenteRepository).obtenerPorId(any(AgentePK.class));
        verify(agenteMapper).toDto(agente);
    }

    @Test
    void loginContrasenaIncorrecta() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agente);
        when(agenteMapper.toDto(agente)).thenReturn(agenteDto);

        // Act
        ResponseServiceDto response = agenteService.login(TIPO_DOCUMENTO, NUM_DOCUMENTO, "contraseñaIncorrecta");

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_401, response.getStatusCode());
        assertEquals(MENSAJE_401, response.getStatusDescription());
        assertTrue(response.getData() instanceof Map);
        assertTrue(((Map<?, ?>) response.getData()).isEmpty());
    }

    @Test
    void loginAgenteNoEncontrado() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class))).thenReturn(agente);
        when(agenteMapper.toDto(agente)).thenReturn(null);

        // Act
        ResponseServiceDto response = agenteService.login(TIPO_DOCUMENTO, NUM_DOCUMENTO, CONTRASENA);

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_206, response.getStatusCode());
        assertEquals(MENSAJE_206, response.getStatusDescription());
        assertTrue(response.getData() instanceof Map);
        assertTrue(((Map<?, ?>) response.getData()).isEmpty());
    }

    @Test
    void loginErrorNumeroDocumentoInvalido() {
        // Act
        ResponseServiceDto response = agenteService.login(TIPO_DOCUMENTO, "numeroInvalido", CONTRASENA);

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_500, response.getStatusCode());
        assertEquals(MENSAJE_500, response.getStatusDescription());
        assertTrue(response.getData() instanceof String);
    }

    @Test
    void loginErrorRepositorio() {
        // Arrange
        when(agenteRepository.obtenerPorId(any(AgentePK.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        // Act
        ResponseServiceDto response = agenteService.login(TIPO_DOCUMENTO, NUM_DOCUMENTO, CONTRASENA);

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_500, response.getStatusCode());
        assertEquals(MENSAJE_500, response.getStatusDescription());
        assertEquals("Error de base de datos", response.getData());
    }

    @Test
    void loginParametrosNulos() {
        // Act
        ResponseServiceDto response = agenteService.login(null, null, null);

        // Assert
        assertNotNull(response);
        assertEquals(CODIGO_500, response.getStatusCode());
        assertEquals(MENSAJE_500, response.getStatusDescription());
        assertTrue(response.getData() instanceof String);
    }
}