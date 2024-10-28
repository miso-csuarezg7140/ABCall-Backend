package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.persistence.entity.Agente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgenteMapper {

    AgenteDto toDto(Agente agente);

    List<AgenteDto> toDtoList(List<Agente> agenteList);

    @InheritInverseConfiguration
    Agente toEntity(AgenteDto agenteDto);

    List<Agente> toEntityList(List<AgenteDto> agenteDtoList);
}
