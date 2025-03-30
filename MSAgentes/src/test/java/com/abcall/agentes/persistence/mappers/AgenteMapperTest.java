package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgenteMapperTest {

    private AgenteMapper mapper;
    private static final String TIPO_DOCUMENTO = "CC";
    private static final Long NUMERO_DOCUMENTO = 123456789L;
    private static final String CONTRASENA = "password123";
    private static final String NOMBRES = "Juan";
    private static final String APELLIDOS = "Pérez";

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AgenteMapper.class);
    }

    @Test
    void toDtoWhenAgenteIsNotNull() {
        // Arrange
        AgentePK agentePK = new AgentePK();
        agentePK.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK.setNumeroDocumento(NUMERO_DOCUMENTO);

        Agente agente = new Agente();
        agente.setAgentePK(agentePK);
        agente.setContrasena(CONTRASENA);
        agente.setNombres(NOMBRES);
        agente.setApellidos(APELLIDOS);

        // Act
        AgenteDto result = mapper.toDto(agente);

        // Assert
        assertNotNull(result);
        assertEquals(TIPO_DOCUMENTO, result.getTipoDocumento());
        assertEquals(NUMERO_DOCUMENTO, result.getNumeroDocumento());
        assertEquals(CONTRASENA, result.getContrasena());
        assertEquals(NOMBRES, result.getNombres());
        assertEquals(APELLIDOS, result.getApellidos());
    }

    @Test
    void toDtoWhenAgenteIsNull() {
        // Act
        AgenteDto result = mapper.toDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDtoListWhenListIsNotEmpty() {
        // Arrange
        AgentePK agentePK1 = new AgentePK();
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        Agente agente1 = new Agente();
        agente1.setAgentePK(agentePK1);
        agente1.setContrasena(CONTRASENA);
        agente1.setNombres(NOMBRES);
        agente1.setApellidos(APELLIDOS);

        AgentePK agentePK2 = new AgentePK();
        agentePK2.setTipoDocumento("CE");
        agentePK2.setNumeroDocumento(987654321L);

        Agente agente2 = new Agente();
        agente2.setAgentePK(agentePK2);
        agente2.setContrasena("otherpass");
        agente2.setNombres("María");
        agente2.setApellidos("López");

        List<Agente> agentes = Arrays.asList(agente1, agente2);

        // Act
        List<AgenteDto> result = mapper.toDtoList(agentes);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TIPO_DOCUMENTO, result.get(0).getTipoDocumento());
        assertEquals("CE", result.get(1).getTipoDocumento());
    }

    @Test
    void toDtoListWhenListIsEmpty() {
        // Act
        List<AgenteDto> result = mapper.toDtoList(Collections.emptyList());

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoListWhenListIsNull() {
        // Act
        List<AgenteDto> result = mapper.toDtoList(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntityWhenDtoIsNotNull() {
        // Arrange
        AgenteDto dto = new AgenteDto();
        dto.setTipoDocumento(TIPO_DOCUMENTO);
        dto.setNumeroDocumento(NUMERO_DOCUMENTO);
        dto.setContrasena(CONTRASENA);
        dto.setNombres(NOMBRES);
        dto.setApellidos(APELLIDOS);

        // Act
        Agente result = mapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAgentePK());
        assertEquals(TIPO_DOCUMENTO, result.getAgentePK().getTipoDocumento());
        assertEquals(NUMERO_DOCUMENTO, result.getAgentePK().getNumeroDocumento());
        assertEquals(CONTRASENA, result.getContrasena());
        assertEquals(NOMBRES, result.getNombres());
        assertEquals(APELLIDOS, result.getApellidos());
    }

    @Test
    void toEntityWhenDtoIsNull() {
        // Act
        Agente result = mapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntityListWhenListIsNotEmpty() {
        // Arrange
        AgenteDto dto1 = new AgenteDto();
        dto1.setTipoDocumento(TIPO_DOCUMENTO);
        dto1.setNumeroDocumento(NUMERO_DOCUMENTO);
        dto1.setContrasena(CONTRASENA);
        dto1.setNombres(NOMBRES);
        dto1.setApellidos(APELLIDOS);

        AgenteDto dto2 = new AgenteDto();
        dto2.setTipoDocumento("CE");
        dto2.setNumeroDocumento(987654321L);
        dto2.setContrasena("otherpass");
        dto2.setNombres("María");
        dto2.setApellidos("López");

        List<AgenteDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<Agente> result = mapper.toEntityList(dtos);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TIPO_DOCUMENTO, result.get(0).getAgentePK().getTipoDocumento());
        assertEquals("CE", result.get(1).getAgentePK().getTipoDocumento());
    }

    @Test
    void toEntityListWhenListIsEmpty() {
        // Act
        List<Agente> result = mapper.toEntityList(Collections.emptyList());

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntityListWhenListIsNull() {
        // Act
        List<Agente> result = mapper.toEntityList(null);

        // Assert
        assertNull(result);
    }
}
