package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.UserClientDto;
import com.abcall.clientes.persistence.entity.UserClient;
import com.abcall.clientes.persistence.mappers.IUserClientMapper;
import com.abcall.clientes.persistence.repository.IUserClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserClientRepositoryImpl implements IUserClientRepository {

    private final IUserClientRepositoryJpa userClientRepositoryJpa;
    private final IUserClientMapper userClientMapper;

    @Override
    public UserClientDto findById(UserClientDto userClientDto) {
        UserClient userClient = userClientMapper.toEntity(userClientDto);
        UserClient userClientFound = userClientRepositoryJpa.findById(userClient.getUserClientPK()).orElse(null);
        return userClientMapper.toDto(userClientFound);
    }
}
