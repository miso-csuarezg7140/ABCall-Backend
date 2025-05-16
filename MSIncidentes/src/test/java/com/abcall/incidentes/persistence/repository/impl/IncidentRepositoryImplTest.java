package com.abcall.incidentes.persistence.repository.impl;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.persistence.entity.Incident;
import com.abcall.incidentes.persistence.mappers.IIncidentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentRepositoryImplTest {

    @Mock
    private IIncidentRepositoryJpa incidenteRepositoryJpa;

    @Mock
    private IIncidentMapper incidenteMapper;

    @InjectMocks
    private IncidentRepositoryImpl incidentRepository;

    @Test
    void getIncidentsReturnsMappedPageWhenDataExists() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Incident> incidentPage = new PageImpl<>(List.of(new Incident()));
        IncidentResponse expectedResponse = new IncidentResponse();
        Page<IncidentResponse> responsePage = new PageImpl<>(List.of(expectedResponse));
        when(incidenteRepositoryJpa.getIncidents(123L, 1, "ABC123", "ACTIVO", null, null, false, pageable))
                .thenReturn(incidentPage);
        when(incidenteMapper.toDtoResponse(org.mockito.ArgumentMatchers.any(Incident.class)))
                .thenReturn(expectedResponse);

        Page<IncidentResponse> result = incidentRepository.getIncidents(123L, 1, "ABC123", "ACTIVO", null, null, false, pageable);

        assertEquals(responsePage.getContent(), result.getContent());
        assertEquals(responsePage.getTotalElements(), result.getTotalElements());
        assertEquals(responsePage.getNumber(), result.getNumber());
    }

    @Test
    void getIncidentsReturnsNullWhenNoDataExists() {
        Pageable pageable = PageRequest.of(0, 10);
        when(incidenteRepositoryJpa.getIncidents(123L, 1, "ABC123", "ACTIVO", null, null, false, pageable))
                .thenReturn(null);

        Page<IncidentResponse> result = incidentRepository.getIncidents(123L, 1, "ABC123", "ACTIVO", null, null, false, pageable);

        assertNull(result);
    }

    @Test
    void findByIdReturnsMappedResponseWhenIdExists() {
        Incident incident = new Incident();
        DetailIncidentResponse response = new DetailIncidentResponse();
        when(incidenteRepositoryJpa.findById(1)).thenReturn(Optional.of(incident));
        when(incidenteMapper.toDtoDetalleResponse(incident)).thenReturn(response);

        DetailIncidentResponse result = incidentRepository.findById(1);

        assertEquals(response, result);
    }

    @Test
    void findByIdReturnsNullWhenIdDoesNotExist() {
        when(incidenteRepositoryJpa.findById(1)).thenReturn(Optional.empty());

        DetailIncidentResponse result = incidentRepository.findById(1);

        assertNull(result);
    }

    @Test
    void createReturnsMappedRequestAfterSaving() {
        CreateIncidentRequest request = new CreateIncidentRequest();
        Incident incident = new Incident();
        CreateIncidentRequest mappedRequest = new CreateIncidentRequest();
        when(incidenteMapper.toEntity(request)).thenReturn(incident);
        when(incidenteRepositoryJpa.save(incident)).thenReturn(incident);
        when(incidenteMapper.toDtoCrearRequest(incident)).thenReturn(mappedRequest);

        CreateIncidentRequest result = incidentRepository.create(request);

        assertEquals(mappedRequest, result);
    }

    @Test
    void updateReturnsMappedResponseWhenIncidentExists() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        Incident incident = new Incident();
        DetailIncidentResponse response = new DetailIncidentResponse();
        when(incidenteMapper.toEntityActualizar(request)).thenReturn(incident);
        when(incidenteRepositoryJpa.existsById(incident.getId())).thenReturn(true);
        when(incidenteRepositoryJpa.save(incident)).thenReturn(incident);
        when(incidenteMapper.toDtoDetalleResponse(incident)).thenReturn(response);

        DetailIncidentResponse result = incidentRepository.update(request);

        assertEquals(response, result);
    }

    @Test
    void updateReturnsNullWhenIncidentDoesNotExist() {
        UpdateIncidentRequest request = new UpdateIncidentRequest();
        Incident incident = new Incident();
        when(incidenteMapper.toEntityActualizar(request)).thenReturn(incident);
        when(incidenteRepositoryJpa.existsById(incident.getId())).thenReturn(false);

        DetailIncidentResponse result = incidentRepository.update(request);

        assertNull(result);
    }
}