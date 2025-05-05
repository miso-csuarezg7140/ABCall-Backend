package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.persistence.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClientMapper {

    Client toEntity(ClientDto clientDto);

    ClientDto toDto(Client client);

    List<ListClientResponse> toListDtoList(List<Client> clientList);
}
