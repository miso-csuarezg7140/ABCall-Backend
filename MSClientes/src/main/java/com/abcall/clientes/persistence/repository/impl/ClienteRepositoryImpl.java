package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.persistence.repository.ClienteRepository;
import com.abcall.clientes.persistence.repository.jpa.ClienteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryJpa clienteRepositoryJpa;
}
