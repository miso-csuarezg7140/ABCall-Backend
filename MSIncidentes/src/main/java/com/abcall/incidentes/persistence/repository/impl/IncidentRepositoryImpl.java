package com.abcall.incidentes.persistence.repository.impl;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.persistence.entity.Incident;
import com.abcall.incidentes.persistence.mappers.IIncidentMapper;
import com.abcall.incidentes.persistence.repository.IIncidentRepository;
import com.abcall.incidentes.persistence.repository.jpa.IIncidentRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class IncidentRepositoryImpl implements IIncidentRepository {

    private final IIncidentRepositoryJpa incidenteRepositoryJpa;
    private final IIncidentMapper incidenteMapper;

    @Override
    public Page<IncidentResponse> getIncidents(Long clientDocumentNumber, Integer userDocumentType,
                                               String userDocumentNumber, String status, LocalDateTime startDate,
                                               LocalDateTime endDate, Boolean withDates, Pageable pageable) {
        Page<Incident> incidentPage = incidenteRepositoryJpa.getIncidents(clientDocumentNumber, userDocumentType,
                userDocumentNumber, status, startDate, endDate, withDates, pageable);

        return null != incidentPage ? incidentPage.map(incidenteMapper::toDtoResponse) : null;
    }

    @Override
    public DetailIncidentResponse findById(Integer idIncidente) {
        return incidenteMapper.toDtoDetalleResponse(incidenteRepositoryJpa.findById(idIncidente).orElse(null));
    }

    @Override
    public CreateIncidentRequest create(CreateIncidentRequest createIncidentRequest) {
        Incident incident = incidenteMapper.toEntity(createIncidentRequest);
        return incidenteMapper.toDtoCrearRequest(incidenteRepositoryJpa.save(incident));
    }

    @Override
    public DetailIncidentResponse update(UpdateIncidentRequest updateIncidentRequest) {
        Incident incident = incidenteMapper.toEntityActualizar(updateIncidentRequest);
        boolean existeIncidente = incidenteRepositoryJpa.existsById(incident.getId());
        return existeIncidente ? incidenteMapper.toDtoDetalleResponse(incidenteRepositoryJpa.save(incident)) : null;
    }
}
