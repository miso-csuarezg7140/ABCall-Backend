package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.persistence.entity.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientMapper {

    Client toEntity(ClientDto clientDto);

    List<Client> toEntityList(List<ClientDto> clientDtoList);

    @InheritInverseConfiguration
    ClientDto toDto(Client client);

    List<ClientDto> toDtoList(List<Client> clientList);
}
