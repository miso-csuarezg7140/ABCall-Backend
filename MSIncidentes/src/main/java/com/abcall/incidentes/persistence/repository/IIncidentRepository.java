package com.abcall.incidentes.persistence.repository;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IIncidentRepository {

    Page<IncidentResponse> getIncidents(Long clientDocumentNumber, Integer userDocumentType, String userDocumentNumber,
                                        String status, LocalDateTime startDate, LocalDateTime endDate, Boolean withDates,
                                        Pageable pageable);

    DetailIncidentResponse findById(Integer incidentId);

    CreateIncidentRequest create(CreateIncidentRequest createIncidentRequest);

    DetailIncidentResponse update(UpdateIncidentRequest updateIncidentRequest);
}
