package com.abcall.clientes.persistence.repository;

import com.abcall.clientes.domain.dto.UserClientDto;

public interface IUserClientRepository {

    UserClientDto findById(UserClientDto userClientDto);
}
