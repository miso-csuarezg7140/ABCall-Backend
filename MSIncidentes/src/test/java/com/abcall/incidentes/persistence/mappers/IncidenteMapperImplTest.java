package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.ActualizarIncidenteRequest;
import com.abcall.incidentes.domain.dto.request.CrearIncidenteRequest;
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
        CrearIncidenteRequest request = new CrearIncidenteRequest();
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
        CrearIncidenteRequest request1 = new CrearIncidenteRequest();
        request1.setTipoDocumentoUsuario("CC");
        request1.setNumDocumentoUsuario("123456789");

        CrearIncidenteRequest request2 = new CrearIncidenteRequest();
        request2.setTipoDocumentoUsuario("TI");
        request2.setNumDocumentoUsuario("987654321");

        List<CrearIncidenteRequest> requestList = List.of(request1, request2);

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
    void toDto_Request_shouldMapAllFieldsCorrectly() {
        Incidente incidente = new Incidente();
        incidente.setTipoDocumentoUsuario("CC");
        incidente.setNumDocumentoUsuario(123456789L);
        incidente.setNumDocumentoCliente(987654321L);
        incidente.setDescripcion("Test description");
        incidente.setSolucionado(true);
        incidente.setEstado("OPEN");
        incidente.setCreadoPor("user1");
        incidente.setFechaCreacion(LocalDateTime.of(2025, 4, 27, 0, 0));

        CrearIncidenteRequest result = mapper.toDtoCrearRequest(incidente);

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
    void toDto_Request_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoCrearRequest(null));
    }

    @Test
    void toDtoCrearRequestList_shouldMapListCorrectly() {
        Incidente incidente1 = new Incidente();
        incidente1.setTipoDocumentoUsuario("CC");
        incidente1.setNumDocumentoUsuario(123456789L);

        Incidente incidente2 = new Incidente();
        incidente2.setTipoDocumentoUsuario("TI");
        incidente2.setNumDocumentoUsuario(987654321L);

        List<Incidente> incidenteList = List.of(incidente1, incidente2);

        List<CrearIncidenteRequest> result = mapper.toDtoCrearRequestList(incidenteList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals("123456789", result.get(0).getNumDocumentoUsuario());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
        assertEquals("987654321", result.get(1).getNumDocumentoUsuario());
    }

    @Test
    void toDtoCrearRequestList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<CrearIncidenteRequest> result = mapper.toDtoCrearRequestList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoCrearRequestList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoCrearRequestList(null));
    }

    @Test
    void toDtoCrearRequestResponse_shouldMapAllFieldsCorrectly() {
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
    void toDtoCrearRequestResponse_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoResponse(null));
    }

    @Test
    void toDtoCrearRequestResponseList_shouldMapListCorrectly() {
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
    void toDtoCrearRequestResponseList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<IncidenteResponse> result = mapper.toDtoResponseList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoCrearRequestResponseList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoResponseList(null));
    }

    @Test
    void toDtoCrearRequestDetalleResponse_shouldMapAllFieldsCorrectly() {
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
    void toDtoCrearRequestDetalleResponse_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoDetalleResponse(null));
    }

    @Test
    void toDtoCrearRequestDetalleResponseList_shouldMapListCorrectly() {
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
    void toDtoCrearRequestDetalleResponseList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<IncidenteDetalleResponse> result = mapper.toDtoDetalleResponseList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoCrearRequestDetalleResponseList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoDetalleResponseList(null));
    }

    @Test
    void toDtoActualizarRequest_shouldMapAllFieldsCorrectly() {
        Incidente incidente = new Incidente();
        incidente.setTipoDocumentoUsuario("CC");
        incidente.setNumDocumentoUsuario(123456789L);
        incidente.setNumDocumentoCliente(987654321L);
        incidente.setSolucionado(true);
        incidente.setEstado("OPEN");
        incidente.setModificadoPor("user1");
        incidente.setFechaModificacion(LocalDateTime.of(2025, 4, 27, 0, 0));

        ActualizarIncidenteRequest result = mapper.toDtoActualizarRequest(incidente);

        assertNotNull(result);
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals("123456789", result.getNumDocumentoUsuario());
        assertEquals("987654321", result.getNumDocumentoCliente());
        assertTrue(result.getSolucionado());
        assertEquals("OPEN", result.getEstado());
        assertEquals("user1", result.getModificadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 27, 0, 0), result.getFechaModificacion());
    }

    @Test
    void toDtoActualizarRequest_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoActualizarRequest(null));
    }

    @Test
    void toDtoActualizarRequestList_shouldMapListCorrectly() {
        Incidente incidente1 = new Incidente();
        incidente1.setTipoDocumentoUsuario("CC");
        incidente1.setNumDocumentoUsuario(123456789L);

        Incidente incidente2 = new Incidente();
        incidente2.setTipoDocumentoUsuario("TI");
        incidente2.setNumDocumentoUsuario(987654321L);

        List<Incidente> incidenteList = List.of(incidente1, incidente2);

        List<ActualizarIncidenteRequest> result = mapper.toDtoActualizarRequestList(incidenteList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals("123456789", result.get(0).getNumDocumentoUsuario());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
        assertEquals("987654321", result.get(1).getNumDocumentoUsuario());
    }

    @Test
    void toDtoActualizarRequestList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<ActualizarIncidenteRequest> result = mapper.toDtoActualizarRequestList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoActualizarRequestList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toDtoActualizarRequestList(null));
    }

    @Test
    void toEntityActualizar_shouldMapAllFieldsCorrectly() {
        ActualizarIncidenteRequest request = new ActualizarIncidenteRequest();
        request.setTipoDocumentoUsuario("CC");
        request.setNumDocumentoUsuario("123456789");
        request.setNumDocumentoCliente("987654321");
        request.setSolucionado(true);
        request.setEstado("OPEN");
        request.setModificadoPor("user1");
        request.setFechaModificacion(LocalDateTime.of(2025, 4, 27, 0, 0));

        Incidente result = mapper.toEntityActualizar(request);

        assertNotNull(result);
        assertEquals("CC", result.getTipoDocumentoUsuario());
        assertEquals(123456789L, result.getNumDocumentoUsuario());
        assertEquals(987654321L, result.getNumDocumentoCliente());
        assertTrue(result.getSolucionado());
        assertEquals("OPEN", result.getEstado());
        assertEquals("user1", result.getModificadoPor());
        assertEquals(LocalDateTime.of(2025, 4, 27, 0, 0), result.getFechaModificacion());
    }

    @Test
    void toEntityActualizar_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toEntityActualizar(null));
    }

    @Test
    void toEntityActualizarList_shouldMapListCorrectly() {
        ActualizarIncidenteRequest request1 = new ActualizarIncidenteRequest();
        request1.setTipoDocumentoUsuario("CC");
        request1.setNumDocumentoUsuario("123456789");

        ActualizarIncidenteRequest request2 = new ActualizarIncidenteRequest();
        request2.setTipoDocumentoUsuario("TI");
        request2.setNumDocumentoUsuario("987654321");

        List<ActualizarIncidenteRequest> requestList = List.of(request1, request2);

        List<Incidente> result = mapper.toEntityActualizarList(requestList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CC", result.get(0).getTipoDocumentoUsuario());
        assertEquals(123456789L, result.get(0).getNumDocumentoUsuario());
        assertEquals("TI", result.get(1).getTipoDocumentoUsuario());
        assertEquals(987654321L, result.get(1).getNumDocumentoUsuario());
    }

    @Test
    void toEntityActualizarList_shouldReturnEmptyListWhenInputIsEmpty() {
        List<Incidente> result = mapper.toEntityActualizarList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntityActualizarList_shouldReturnNullWhenInputIsNull() {
        assertNull(mapper.toEntityActualizarList(null));
    }
}