package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.AgentDto;
import com.abcall.agentes.persistence.entity.Agent;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAgentMapper {

    @Mapping(source = "agentPK.documentType", target = "documentType")
    @Mapping(source = "agentPK.documentNumber", target = "documentNumber")
    AgentDto toDto(Agent agent);

    @InheritInverseConfiguration
    Agent toEntity(AgentDto agentDto);
}
