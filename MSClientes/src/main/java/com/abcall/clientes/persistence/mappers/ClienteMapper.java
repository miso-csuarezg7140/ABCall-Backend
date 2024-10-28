package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClienteDto;
import com.abcall.clientes.persistence.entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDto clienteDto);

    List<Cliente> toEntityList(List<ClienteDto> clienteDtoList);

    @InheritInverseConfiguration
    ClienteDto toDto(Cliente cliente);

    List<ClienteDto> toDtoList(List<Cliente> clienteList);
}
