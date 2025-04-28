package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.domain.dto.response.IncidenteDetalleResponse;
import com.abcall.incidentes.domain.dto.response.IncidenteResponse;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    Incidente toEntity(IncidenteRequest incidenteRequest);

    List<Incidente> toEntityList(List<IncidenteRequest> incidenteRequestList);

    IncidenteRequest toDto(Incidente incidente);

    List<IncidenteRequest> toDtoList(List<Incidente> incidenteRequestList);

    IncidenteResponse toDtoResponse(Incidente incidente);

    List<IncidenteResponse> toDtoResponseList(List<Incidente> incidenteDtoList);

    IncidenteDetalleResponse toDtoDetalleResponse(Incidente incidente);

    List<IncidenteDetalleResponse> toDtoDetalleResponseList(List<Incidente> incidenteDtoList);
}
