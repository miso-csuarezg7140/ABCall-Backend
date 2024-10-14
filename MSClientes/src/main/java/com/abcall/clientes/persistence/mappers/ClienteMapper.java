package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClienteDto;
import com.abcall.clientes.persistence.entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toCliente(ClienteDto clienteDto);

    @InheritInverseConfiguration
    ClienteDto fromCliente(Cliente cliente);
}
