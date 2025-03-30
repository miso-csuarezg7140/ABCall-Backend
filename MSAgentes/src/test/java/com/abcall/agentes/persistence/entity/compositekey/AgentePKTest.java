package com.abcall.agentes.persistence.entity.compositekey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AgentePKTest {

    private AgentePK agentePK1;
    private AgentePK agentePK2;
    private static final String TIPO_DOCUMENTO = "CC";
    private static final Long NUMERO_DOCUMENTO = 123456789L;

    @BeforeEach
    void setUp() {
        agentePK1 = new AgentePK();
        agentePK2 = new AgentePK();
    }

    @Test
    void testConstructorVacio() {
        // Assert
        assertNotNull(agentePK1);
        assertNull(agentePK1.getTipoDocumento());
        assertNull(agentePK1.getNumeroDocumento());
    }

    @Test
    void testSetAndGetTipoDocumento() {
        // Act
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);

        // Assert
        assertEquals(TIPO_DOCUMENTO, agentePK1.getTipoDocumento());
    }

    @Test
    void testSetAndGetNumeroDocumento() {
        // Act
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(NUMERO_DOCUMENTO, agentePK1.getNumeroDocumento());
    }

    @Test
    void testEquals() {
        // Arrange
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        agentePK2.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK2.setNumeroDocumento(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(agentePK1, agentePK2);
        assertEquals(agentePK1, agentePK1); // reflexividad
    }

    @Test
    void testNotEquals() {
        // Arrange
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        agentePK2.setTipoDocumento("CE");
        agentePK2.setNumeroDocumento(NUMERO_DOCUMENTO);

        // Assert
        assertNotEquals(agentePK1, agentePK2);
        assertNotEquals(agentePK1, null);
        assertNotEquals(agentePK1, new Object());
    }

    @Test
    void testHashCode() {
        // Arrange
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        agentePK2.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK2.setNumeroDocumento(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(agentePK1.hashCode(), agentePK2.hashCode());
    }

    @Test
    void testDifferentHashCode() {
        // Arrange
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK1.setNumeroDocumento(NUMERO_DOCUMENTO);

        agentePK2.setTipoDocumento("CE");
        agentePK2.setNumeroDocumento(987654321L);

        // Assert
        assertNotEquals(agentePK1.hashCode(), agentePK2.hashCode());
    }

    @Test
    void testNullFields() {
        // Assert
        assertEquals(agentePK1, agentePK2);
        assertEquals(agentePK1.hashCode(), agentePK2.hashCode());
    }

    @Test
    void testPartiallyNullFields() {
        // Arrange
        agentePK1.setTipoDocumento(TIPO_DOCUMENTO);
        agentePK2.setTipoDocumento(TIPO_DOCUMENTO);

        // Assert
        assertEquals(agentePK1, agentePK2);
        assertEquals(agentePK1.hashCode(), agentePK2.hashCode());
    }
}
