package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.persistence.entity.Incident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IIncidentMapperTest {

    @Test
    void toEntityReturnsNullWhenCreateIncidentRequestIsNull() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();

        Incident result = mapper.toEntity(null);

        assertNull(result);
    }

    @Test
    void toEntityMapsAllFieldsCorrectly() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();
        CreateIncidentRequest request = new CreateIncidentRequest();
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("123456789");
        request.setDescription("Test description");
        request.setSolved(false);
        request.setStatus("ACTIVE");
        request.setCreatedBy("admin");
        request.setCreatedDate(LocalDateTime.now());

        Incident result = mapper.toEntity(request);

        assertNotNull(result);
        assertEquals(1, result.getUserDocumentType());
        assertEquals("123456789", result.getUserDocumentNumber());
        assertEquals("Test description", result.getDescription());
        assertFalse(result.getSolved());
        assertEquals("ACTIVE", result.getStatus());
        assertEquals("admin", result.getCreatedBy());
        assertNotNull(result.getCreatedDate());
    }

    @Test
    void toDtoResponseReturnsNullWhenIncidentIsNull() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();

        IncidentResponse result = mapper.toDtoResponse(null);

        assertNull(result);
    }

    @Test
    void toDtoResponseMapsAllFieldsCorrectly() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();
        Incident incident = new Incident();
        incident.setId(1);
        incident.setUserDocumentType(1);
        incident.setUserDocumentNumber("123456789");
        incident.setClientDocumentNumber(987654321L);
        incident.setDescription("Test description");
        incident.setStatus("ACTIVE");
        incident.setCreatedDate(LocalDateTime.now());

        IncidentResponse result = mapper.toDtoResponse(incident);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getUserDocumentType());
        assertEquals("123456789", result.getUserDocumentNumber());
        assertEquals(987654321L, result.getClientDocumentNumber());
        assertEquals("Test description", result.getDescription());
        assertEquals("ACTIVE", result.getStatus());
        assertNotNull(result.getCreatedDate());
    }

    @Test
    void toEntityActualizarReturnsNullWhenUpdateIncidentRequestIsNull() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();

        Incident result = mapper.toEntityActualizar(null);

        assertNull(result);
    }

    @Test
    void toEntityActualizarMapsAllFieldsCorrectly() {
        IIncidentMapperImpl mapper = new IIncidentMapperImpl();
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        request.setUserDocumentType("1");
        request.setUserDocumentNumber("123456789");
        request.setClientDocumentNumber("987654321");
        request.setSolved(true);
        request.setStatus("SOLVED");
        request.setModifiedBy("editor");
        request.setModifiedDate(LocalDateTime.now());

        Incident result = mapper.toEntityActualizar(request);

        assertNotNull(result);
        assertEquals(1, result.getUserDocumentType());
        assertEquals("123456789", result.getUserDocumentNumber());
        assertEquals(987654321L, result.getClientDocumentNumber());
        assertTrue(result.getSolved());
        assertEquals("SOLVED", result.getStatus());
        assertEquals("editor", result.getModifiedBy());
        assertNotNull(result.getModifiedDate());
    }
}