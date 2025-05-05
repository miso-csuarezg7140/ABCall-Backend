package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.persistence.entity.UserClient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserClientMapper {

    @Mapping(source = "documentTypeUser", target = "userClientPK.documentTypeUser")
    @Mapping(source = "documentUser", target = "userClientPK.documentUser")
    @Mapping(source = "idClient", target = "userClientPK.idClient")
    UserClient toEntity(UserClientDto userClientDto);

    @InheritInverseConfiguration
    UserClientDto toDto(UserClient userClient);
}
