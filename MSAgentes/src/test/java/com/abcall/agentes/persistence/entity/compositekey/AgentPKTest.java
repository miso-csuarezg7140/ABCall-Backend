package com.abcall.agentes.persistence.entity.compositekey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AgentPKTest {

    private static final Integer TIPO_DOCUMENTO = 1;
    private static final String NUMERO_DOCUMENTO = "123456789";
    private AgentPK agentPK1;
    private AgentPK agentPK2;

    @BeforeEach
    void setUp() {
        agentPK1 = new AgentPK();
        agentPK2 = new AgentPK();
    }

    @Test
    void testConstructorVacio() {
        // Assert
        assertNotNull(agentPK1);
        assertNull(agentPK1.getDocumentType());
        assertNull(agentPK1.getDocumentNumber());
    }

    @Test
    void testSetAndGetTipoDocumento() {
        // Act
        agentPK1.setDocumentType(TIPO_DOCUMENTO);

        // Assert
        assertEquals(TIPO_DOCUMENTO, agentPK1.getDocumentType());
    }

    @Test
    void testSetAndGetNumeroDocumento() {
        // Act
        agentPK1.setDocumentNumber(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(NUMERO_DOCUMENTO, agentPK1.getDocumentNumber());
    }

    @Test
    void testEquals() {
        // Arrange
        agentPK1.setDocumentType(TIPO_DOCUMENTO);
        agentPK1.setDocumentNumber(NUMERO_DOCUMENTO);

        agentPK2.setDocumentType(TIPO_DOCUMENTO);
        agentPK2.setDocumentNumber(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(agentPK1, agentPK2);
        assertEquals(agentPK1, agentPK1); // reflexividad
    }

    @Test
    void testNotEquals() {
        // Arrange
        agentPK1.setDocumentType(TIPO_DOCUMENTO);
        agentPK1.setDocumentNumber(NUMERO_DOCUMENTO);

        agentPK2.setDocumentType(2);
        agentPK2.setDocumentNumber(NUMERO_DOCUMENTO);

        // Assert
        assertNotEquals(agentPK1, agentPK2);
        assertNotEquals(null, agentPK1);
        assertNotEquals(new Object(), agentPK1);
    }

    @Test
    void testHashCode() {
        // Arrange
        agentPK1.setDocumentType(TIPO_DOCUMENTO);
        agentPK1.setDocumentNumber(NUMERO_DOCUMENTO);

        agentPK2.setDocumentType(TIPO_DOCUMENTO);
        agentPK2.setDocumentNumber(NUMERO_DOCUMENTO);

        // Assert
        assertEquals(agentPK1.hashCode(), agentPK2.hashCode());
    }

    @Test
    void testDifferentHashCode() {
        // Arrange
        agentPK1.setDocumentType(TIPO_DOCUMENTO);
        agentPK1.setDocumentNumber(NUMERO_DOCUMENTO);

        agentPK2.setDocumentType(2);
        agentPK2.setDocumentNumber("987654321");

        // Assert
        assertNotEquals(agentPK1.hashCode(), agentPK2.hashCode());
    }

    @Test
    void testNullFields() {
        // Assert
        assertEquals(agentPK1, agentPK2);
        assertEquals(agentPK1.hashCode(), agentPK2.hashCode());
    }

    @Test
    void testPartiallyNullFields() {
        // Arrange
        agentPK1.setDocumentType(TIPO_DOCUMENTO);
        agentPK2.setDocumentType(TIPO_DOCUMENTO);

        // Assert
        assertEquals(agentPK1, agentPK2);
        assertEquals(agentPK1.hashCode(), agentPK2.hashCode());
    }
}
