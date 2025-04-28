package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.domain.dto.response.IncidenteDetalleResponse;
import com.abcall.incidentes.domain.dto.response.IncidenteResponse;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncidenteMapperImplTest {

    private final IncidenteMapperImpl mapper = new IncidenteMapperImpl();

    private static Incidente getIncidente() {
        Incidente incidente = new Incidente();
        incidente.setId(1);
        incidente.setTipoDocumentoUsuario("CC");
        incidente.setNumDocumentoUsuario(123456789L);
        incidente.setNumDocumentoCliente(987654321L);
        incidente.setDescripcion("Test description");
        incidente.setSolucionado(true);
        incidente.setSolucionId(100);
        incidente.setSolucionadoPor("user2");
        incidente.setFechaSolucion(LocalDateTime.of(2025, 4, 28, 0, 0));
        incidente.setEstado("CLOSED");
        incidente.setCreadoPor("user1");
        incidente.setFechaCreacion(LocalDateTime.of(2025, 4, 27, 0, 0));
        incidente.setModificadoPor("user3");
        incidente.setFechaModificacion(LocalDateTime.of(2025, 4, 29, 0, 0));
        return incidente;
    }

    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        IncidenteRequest request = new IncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario("123456789");
        request.setNumDocumentoCliente("987654321");
        request.setDescripcion("Test description");
        request.setSolucionado(true);
        request.setEstado("OPEN");
        request.setCreadoPor("user1");
        request.setFechaCreacion(LocalDateTime.of(2025, 4, 27, 0, 0));

        Incidente result = mapper.toEntity(request);

        assertNotNull(result);
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals(123456789L, result.getNumDocumentoUsuario());
        assertEquals(987654321L, result.getNumDocumentoCliente());
        assertEquals("Test description", result.getDescripcion());
        assertTrue(result.getSolucionado());
        assertEquals("OPEN", result.getEstado());
        assertEquals("user1", result.getCreadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 27, 0, 0), result.getFechaCreacion());
    }

    @Test
    void toEntity_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toEntityList_shouldMapListCorrectly() {
        IncidenteRequest request1 = new IncidenteRequest();
        request1.setTipoDocumentoUsuario("CC");
        request1.setNumDocumentoUsuario("123456789");

        IncidenteRequest request2 = new IncidenteRequest();
        request2.setTipoDocumentoUsuario("TI");
        request2.setNumDocumentoUsuario("987654321");

        List<IncidenteRequest> requestList = List.of(request1, request2);

        List<Incidente> result = mapper.toEntityList(requestList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals(123456789L, result.get(0).getNumDocumentoUsuario());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
        assertEquals(987654321L, result.get(1).getNumDocumentoUsuario());
    }

    @Test
    void toEntityList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<Incidente> result = mapper.toEntityList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntityList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toEntityList(null));
    }

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        Incidente incidente = new Incidente();
        incidente.setTipoDocumentoUsuario("CC");
        incidente.setNumDocumentoUsuario(123456789L);
        incidente.setNumDocumentoCliente(987654321L);
        incidente.setDescripcion("Test description");
        incidente.setSolucionado(true);
        incidente.setEstado("OPEN");
        incidente.setCreadoPor("user1");
        incidente.setFechaCreacion(LocalDateTime.of(2025, 4, 27, 0, 0));

        IncidenteRequest result = mapper.toDto(incidente);

        assertNotNull(result);
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals("123456789", result.getNumDocumentoUsuario());
        assertEquals("987654321", result.getNumDocumentoCliente());
        assertEquals("Test description", result.getDescripcion());
        assertTrue(result.getSolucionado());
        assertEquals("OPEN", result.getEstado());
        assertEquals("user1", result.getCreadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 27, 0, 0), result.getFechaCreacion());
    }

    @Test
    void toDto_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toDtoList_shouldMapListCorrectly() {
        Incidente incidente1 = new Incidente();
        incidente1.setTipoDocumentoUsuario("CC");
        incidente1.setNumDocumentoUsuario(123456789L);

        Incidente incidente2 = new Incidente();
        incidente2.setTipoDocumentoUsuario("TI");
        incidente2.setNumDocumentoUsuario(987654321L);

        List<Incidente> incidenteList = List.of(incidente1, incidente2);

        List<IncidenteRequest> result = mapper.toDtoList(incidenteList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals("123456789", result.get(0).getNumDocumentoUsuario());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
        assertEquals("987654321", result.get(1).getNumDocumentoUsuario());
    }

    @Test
    void toDtoList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<IncidenteRequest> result = mapper.toDtoList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    void toDtoResponse_shouldMapAllFieldsCorrectly() {
        Incidente incidente = new Incidente();
        incidente.setId(1);
        incidente.setTipoDocumentoUsuario("CC");
        incidente.setNumDocumentoUsuario(123456789L);
        incidente.setNumDocumentoCliente(987654321L);
        incidente.setDescripcion("Test description");

        IncidenteResponse result = mapper.toDtoResponse(incidente);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals(123456789L, result.getNumDocumentoUsuario());
        assertEquals(987654321L, result.getNumDocumentoCliente());
        assertEquals("Test description", result.getDescripcion());
    }

    @Test
    void toDtoResponse_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoResponse(null));
    }

    @Test
    void toDtoResponseList_shouldMapListCorrectly() {
        Incidente incidente1 = new Incidente();
        incidente1.setId(1);
        incidente1.setTipoDocumentoUsuario("CC");

        Incidente incidente2 = new Incidente();
        incidente2.setId(2);
        incidente2.setTipoDocumentoUsuario("TI");

        List<Incidente> incidenteList = List.of(incidente1, incidente2);

        List<IncidenteResponse> result = mapper.toDtoResponseList(incidenteList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals(2, result.get(1).getId());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
    }

    @Test
    void toDtoResponseList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<IncidenteResponse> result = mapper.toDtoResponseList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoResponseList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoResponseList(null));
    }

    @Test
    void toDtoDetalleResponse_shouldMapAllFieldsCorrectly() {
        Incidente incidente = getIncidente();

        IncidenteDetalleResponse result = mapper.toDtoDetalleResponse(incidente);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals(123456789L, result.getNumDocumentoUsuario());
        assertEquals(987654321L, result.getNumDocumentoCliente());
        assertEquals("Test description", result.getDescripcion());
        assertTrue(result.getSolucionado());
        assertEquals(100, result.getSolucionId());
        assertEquals("user2", result.getSolucionadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 28, 0, 0), result.getFechaSolucion());
        assertEquals("CLOSED", result.getEstado());
        assertEquals("user1", result.getCreadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 27, 0, 0), result.getFechaCreacion());
        assertEquals("user3", result.getModificadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 29, 0, 0), result.getFechaModificacion());
    }

    @Test
    void toDtoDetalleResponse_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoDetalleResponse(null));
    }

    @Test
    void toDtoDetalleResponseList_shouldMapListCorrectly() {
        Incidente incidente1 = new Incidente();
        incidente1.setId(1);
        incidente1.setTipoDocumentoUsuario("CC");

        Incidente incidente2 = new Incidente();
        incidente2.setId(2);
        incidente2.setTipoDocumentoUsuario("TI");

        List<Incidente> incidenteList = List.of(incidente1, incidente2);

        List<IncidenteDetalleResponse> result = mapper.toDtoDetalleResponseList(incidenteList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals(2, result.get(1).getId());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
    }

    @Test
    void toDtoDetalleResponseList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<IncidenteDetalleResponse> result = mapper.toDtoDetalleResponseList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoDetalleResponseList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoDetalleResponseList(null));
    }
}