package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.ActualizarIncidenteRequest;
import com.abcall.incidentes.domain.dto.request.CrearIncidenteRequest;
import com.abcall.incidentes.domain.dto.response.IncidenteDetalleResponse;
import com.abcall.incidentes.domain.dto.response.IncidenteResponse;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    Incidente toEntity(CrearIncidenteRequest crearIncidenteRequest);

    List<Incidente> toEntityList(List<CrearIncidenteRequest> crearIncidenteRequestList);

    CrearIncidenteRequest toDtoCrearRequest(Incidente incidente);

    List<CrearIncidenteRequest> toDtoCrearRequestList(List<Incidente> incidenteRequestList);

    IncidenteResponse toDtoResponse(Incidente incidente);

    List<IncidenteResponse> toDtoResponseList(List<Incidente> incidenteDtoList);

    IncidenteDetalleResponse toDtoDetalleResponse(Incidente incidente);

    List<IncidenteDetalleResponse> toDtoDetalleResponseList(List<Incidente> incidenteDtoList);

    ActualizarIncidenteRequest toDtoActualizarRequest(Incidente incidente);

    List<ActualizarIncidenteRequest> toDtoActualizarRequestList(List<Incidente> incidenteDtoList);

    Incidente toEntityActualizar(ActualizarIncidenteRequest actualizarIncidenteRequest);

    List<Incidente> toEntityActualizarList(List<ActualizarIncidenteRequest> actualizarIncidenteRequestList);
}
