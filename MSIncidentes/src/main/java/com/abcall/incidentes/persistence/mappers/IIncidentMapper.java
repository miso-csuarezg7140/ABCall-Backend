package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.DetailIncidentResponse;
import com.abcall.incidentes.domain.dto.response.IncidentResponse;
import com.abcall.incidentes.persistence.entity.Incident;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IIncidentMapper {

    Incident toEntity(CreateIncidentRequest createIncidentRequest);

    CreateIncidentRequest toDtoCrearRequest(Incident incident);

    IncidentResponse toDtoResponse(Incident incident);

    DetailIncidentResponse toDtoDetalleResponse(Incident incident);

    Incident toEntityActualizar(UpdateIncidentRequest updateIncidentRequest);
}
