package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.repository.jpa.AgenteRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AgenteRepositoryImplTest {

    @Mock
    private AgenteRepositoryJpa agenteRepositoryJpa;

    private AgenteRepositoryImpl agenteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agenteRepository = new AgenteRepositoryImpl(agenteRepositoryJpa);
    }

    @Test
    void obtenerPorId_CuandoExisteAgente_RetornaAgente() {
        // Arrange
        AgentePK agentePK = new AgentePK(); // Configura los valores necesarios del PK
        Agente agenteEsperado = new Agente(); // Configura los valores esperados
        when(agenteRepositoryJpa.findById(agentePK)).thenReturn(Optional.of(agenteEsperado));

        // Act
        Agente resultado = agenteRepository.obtenerPorId(agentePK);

        // Assert
        assertNotNull(resultado);
        assertEquals(agenteEsperado, resultado);
        verify(agenteRepositoryJpa).findById(agentePK);
    }

    @Test
    void obtenerPorId_CuandoNoExisteAgente_RetornaNull() {
        // Arrange
        AgentePK agentePK = new AgentePK(); // Configura los valores necesarios del PK
        when(agenteRepositoryJpa.findById(agentePK)).thenReturn(Optional.empty());

        // Act
        Agente resultado = agenteRepository.obtenerPorId(agentePK);

        // Assert
        assertNull(resultado);
        verify(agenteRepositoryJpa).findById(agentePK);
    }

    @Test
    void obtenerPorId_CuandoPKEsNull_RetornaNull() {
        // Arrange
        when(agenteRepositoryJpa.findById(null)).thenReturn(Optional.empty());

        // Act
        Agente resultado = agenteRepository.obtenerPorId(null);

        // Assert
        assertNull(resultado);
        verify(agenteRepositoryJpa).findById(null);
    }
}
