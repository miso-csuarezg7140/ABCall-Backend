package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    Incidente toEntity(IncidenteDto incidenteDto);

    List<Incidente> toEntityList(List<IncidenteDto> incidenteDtoList);

    @InheritInverseConfiguration
    IncidenteDto toDto(Incidente incidente);

    List<IncidenteDto> toDtoList(List<Incidente> incidenteDtoList);
}
