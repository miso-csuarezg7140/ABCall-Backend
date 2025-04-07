package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.persistence.entity.Client;
import com.abcall.clientes.persistence.mappers.IClientMapper;
import com.abcall.clientes.persistence.repository.IClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClienteRepository {

    private final IClientRepositoryJpa clienteRepositoryJpa;
    private final IClientMapper clientMapper;

    @Override
    public ClientDto findByDocumentNumber(Long documentNumber) {
        Client client = clienteRepositoryJpa.findByDocumentNumber(documentNumber).orElse(null);

        if (null != client)
            return clientMapper.toDto(client);

        return null;
    }

    @Override
    public void save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clienteRepositoryJpa.save(client);
    }
}
