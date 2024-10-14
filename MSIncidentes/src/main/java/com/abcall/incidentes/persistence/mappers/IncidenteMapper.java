package com.abcall.incidentes.persistence.mappers;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.persistence.entity.Incidente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncidenteMapper {

    Incidente toIncidente(IncidenteDto incidenteDto);

    @InheritInverseConfiguration
    IncidenteDto fromIncidente(Incidente incidente);
}
