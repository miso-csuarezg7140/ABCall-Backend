package com.abcall.incidentes.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IncidentTest {

    @Test
    void creationSucceedsWithValidValues() {
        Incident incident = new Incident();
        incident.setId(1);
        incident.setUserDocumentType(1);
        incident.setUserDocumentNumber("123456789");
        incident.setClientDocumentNumber(987654321L);
        incident.setDescription("Test description");
        incident.setSolved(false);
        incident.setIdSolution(null);
        incident.setSolvedBy(null);
        incident.setSolvedDate(null);
        incident.setStatus("ACTIVE");
        incident.setCreatedBy("admin");
        incident.setCreatedDate(LocalDateTime.now());
        incident.setModifiedBy(null);
        incident.setModifiedDate(null);

        assertEquals(1, incident.getId());
        assertEquals(1, incident.getUserDocumentType());
        assertEquals("123456789", incident.getUserDocumentNumber());
        assertEquals(987654321L, incident.getClientDocumentNumber());
        assertEquals("Test description", incident.getDescription());
        assertFalse(incident.getSolved());
        assertEquals("ACTIVE", incident.getStatus());
        assertEquals("admin", incident.getCreatedBy());
        assertNotNull(incident.getCreatedDate());
    }

    @Test
    void modificationUpdatesFieldsCorrectly() {
        Incident incident = new Incident();
        incident.setId(1);
        incident.setUserDocumentType(1);
        incident.setUserDocumentNumber("123456789");
        incident.setClientDocumentNumber(987654321L);
        incident.setDescription("Initial description");
        incident.setSolved(false);
        incident.setStatus("ACTIVE");
        incident.setCreatedBy("admin");
        incident.setCreatedDate(LocalDateTime.now());

        incident.setDescription("Updated description");
        incident.setSolved(true);
        incident.setStatus("SOLVED");
        incident.setModifiedBy("editor");
        incident.setModifiedDate(LocalDateTime.now());

        assertEquals("Updated description", incident.getDescription());
        assertTrue(incident.getSolved());
        assertEquals("SOLVED", incident.getStatus());
        assertEquals("editor", incident.getModifiedBy());
        assertNotNull(incident.getModifiedDate());
    }
}