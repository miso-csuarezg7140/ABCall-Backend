package com.abcall.incidentes.domain.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IncidenteResponseTest {

    @Test
    void shouldSetAndGetIdCorrectly() {
        IncidenteResponse response = new IncidenteResponse();
        response.setId(1);
        assertEquals(1, response.getId());
    }

    @Test
    void shouldSetAndGetTipoDocumentoUsuarioCorrectly() {
        IncidenteResponse response = new IncidenteResponse();
        response.setTipoDocumentoUsuario("DNI");
        assertEquals("DNI", response.getTipoDocumentoUsuario());
    }

    @Test
    void shouldSetAndGetNumDocumentoUsuarioCorrectly() {
        IncidenteResponse response = new IncidenteResponse();
        response.setNumDocumentoUsuario(123456789L);
        assertEquals(123456789L, response.getNumDocumentoUsuario());
    }

    @Test
    void shouldSetAndGetNumDocumentoClienteCorrectly() {
        IncidenteResponse response = new IncidenteResponse();
        response.setNumDocumentoCliente(987654321L);
        assertEquals(987654321L, response.getNumDocumentoCliente());
    }

    @Test
    void shouldSetAndGetDescripcionCorrectly() {
        IncidenteResponse response = new IncidenteResponse();
        response.setDescripcion("Incident description");
        assertEquals("Incident description", response.getDescripcion());
    }

    @Test
    void shouldHandleNullValues() {
        IncidenteResponse response = new IncidenteResponse();
        response.setTipoDocumentoUsuario(null);
        response.setDescripcion(null);
        assertNull(response.getTipoDocumentoUsuario());
        assertNull(response.getDescripcion());
    }
}