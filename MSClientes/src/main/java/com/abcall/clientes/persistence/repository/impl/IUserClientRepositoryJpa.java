package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.persistence.entity.UserClient;
import com.abcall.clientes.persistence.entity.compositekey.UserClientPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserClientRepositoryJpa extends JpaRepository<UserClient, UserClientPK> {
}
