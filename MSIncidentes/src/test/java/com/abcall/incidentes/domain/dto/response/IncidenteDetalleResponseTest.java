package com.abcall.incidentes.domain.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncidenteDetalleResponseTest {

    @Test
    void shouldSetAndGetAllFieldsCorrectly() {
        IncidenteDetalleResponse response = new IncidenteDetalleResponse();

        response.setId(1);
        response.setTipoDocumentoUsuario("DNI");
        response.setNumDocumentoUsuario(12345678L);
        response.setNumDocumentoCliente(87654321L);
        response.setDescripcion("Incident description");
        response.setSolucionado(true);
        response.setSolucionId(100);
        response.setSolucionadoPor("Admin");
        response.setFechaSolucion(LocalDateTime.now());
        response.setEstado("Resolved");
        response.setCreadoPor("User1");
        response.setFechaCreacion(LocalDateTime.now());
        response.setModificadoPor("User2");
        response.setFechaModificacion(LocalDateTime.now());

        assertEquals(1, response.getId());
        assertEquals("DNI", response.getTipoDocumentoUsuario());
        assertEquals(12345678L, response.getNumDocumentoUsuario());
        assertEquals(87654321L, response.getNumDocumentoCliente());
        assertEquals("Incident description", response.getDescripcion());
        assertTrue(response.getSolucionado());
        assertEquals(100, response.getSolucionId());
        assertEquals("Admin", response.getSolucionadoPor());
        assertNotNull(response.getFechaSolucion());
        assertEquals("Resolved", response.getEstado());
        assertEquals("User1", response.getCreadoPor());
        assertNotNull(response.getFechaCreacion());
        assertEquals("User2", response.getModificadoPor());
        assertNotNull(response.getFechaModificacion());
    }

    @Test
    void shouldHandleNullValuesGracefully() {
        IncidenteDetalleResponse response = new IncidenteDetalleResponse();

        response.setTipoDocumentoUsuario(null);
        response.setDescripcion(null);
        response.setSolucionadoPor(null);
        response.setEstado(null);
        response.setCreadoPor(null);
        response.setModificadoPor(null);

        assertNull(response.getTipoDocumentoUsuario());
        assertNull(response.getDescripcion());
        assertNull(response.getSolucionadoPor());
        assertNull(response.getEstado());
        assertNull(response.getCreadoPor());
        assertNull(response.getModificadoPor());
    }

    @Test
    void shouldHandleDefaultValuesForPrimitiveFields() {
        IncidenteDetalleResponse response = new IncidenteDetalleResponse();

        assertNull(response.getId());
        assertNull(response.getNumDocumentoUsuario());
        assertNull(response.getNumDocumentoCliente());
        assertNull(response.getSolucionado());
        assertNull(response.getSolucionId());
        assertNull(response.getFechaSolucion());
        assertNull(response.getFechaCreacion());
        assertNull(response.getFechaModificacion());
    }
}