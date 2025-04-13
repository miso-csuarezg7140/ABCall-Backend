package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.persistence.entity.UserClient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserClientMapper {

    @Mapping(source = "documentTypeUser", target = "userClientPK.documentTypeUser")
    @Mapping(source = "documentNumberUser", target = "userClientPK.documentNumberUser")
    @Mapping(source = "idClient", target = "userClientPK.idClient")
    UserClient toEntity(UserClientDto userClientDto);

    List<UserClient> toEntityList(List<UserClientDto> userClientDtoList);

    @InheritInverseConfiguration
    UserClientDto toDto(UserClient userClient);

    List<UserClientDto> toDtoList(List<UserClient> userClientList);
}
