package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.persistence.entity.Agente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgenteMapper {

    Agente toAgente(AgenteDto agenteDto);

    @InheritInverseConfiguration
    AgenteDto fromAgente(Agente agente);
}
