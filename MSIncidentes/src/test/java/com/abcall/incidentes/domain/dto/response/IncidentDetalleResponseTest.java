package com.abcall.incidentes.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncidentDetalleResponseTest {

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        DetailIncidentResponse response = new DetailIncidentResponse();

        response.setId(1);
        response.setUserDocumentType("DNI");
        response.setUserDocumentNumber(12345678L);
        response.setUserDocumentClient(87654321L);
        response.setDescription("Incident description");
        response.setSolved(true);
        response.setIdSolution(100);
        response.setSolvedBy("Admin");
        response.setSolutionDate(LocalDateTime.now());
        response.setStatus("Resolved");
        response.setCreatedBy("User1");
        response.setCreatedDate(LocalDateTime.now());
        response.setModifiedBy("User2");
        response.setModifiedDate(LocalDateTime.now());

        assertEquals(1, response.getId());
        assertEquals("DNI", response.getUserDocumentType());
        assertEquals(12345678L, response.getUserDocumentNumber());
        assertEquals(87654321L, response.getUserDocumentClient());
        assertEquals("Incident description", response.getDescription());
        assertTrue(response.getSolved());
        assertEquals(100, response.getIdSolution());
        assertEquals("Admin", response.getSolvedBy());
        assertNotNull(response.getSolutionDate());
        assertEquals("Resolved", response.getStatus());
        assertEquals("User1", response.getCreatedBy());
        assertNotNull(response.getCreatedDate());
        assertEquals("User2", response.getModifiedBy());
        assertNotNull(response.getModifiedDate());
    }

    @Test
    void shouldHandleNullValuesGracefully() {
        DetailIncidentResponse response = new DetailIncidentResponse();

        response.setUserDocumentType(null);
        response.setDescription(null);
        response.setSolvedBy(null);
        response.setStatus(null);
        response.setCreatedBy(null);
        response.setModifiedBy(null);

        assertNull(response.getUserDocumentType());
        assertNull(response.getDescription());
        assertNull(response.getSolvedBy());
        assertNull(response.getStatus());
        assertNull(response.getCreatedBy());
        assertNull(response.getModifiedBy());
    }

    @Test
    void shouldHandleDefaultValuesForPrimitiveFields() {
        DetailIncidentResponse response = new DetailIncidentResponse();

        assertNull(response.getId());
        assertNull(response.getUserDocumentNumber());
        assertNull(response.getUserDocumentClient());
        assertNull(response.getSolved());
        assertNull(response.getIdSolution());
        assertNull(response.getSolutionDate());
        assertNull(response.getCreatedDate());
        assertNull(response.getModifiedDate());
    }
}