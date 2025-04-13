package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.request.IncidenteRequest;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    Incidente toEntity(IncidenteRequest incidenteRequest);

    List<Incidente> toEntityList(List<IncidenteRequest> incidenteRequestList);

    @InheritInverseConfiguration
    IncidenteRequest toDto(Incidente incidente);

    List<IncidenteRequest> toDtoList(List<Incidente> incidenteDtoList);
}
