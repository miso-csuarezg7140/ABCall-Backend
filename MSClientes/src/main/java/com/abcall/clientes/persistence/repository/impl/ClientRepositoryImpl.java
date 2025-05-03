package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;
import com.abcall.clientes.persistence.entity.Client;
import com.abcall.clientes.persistence.mappers.IClientMapper;
import com.abcall.clientes.persistence.repository.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

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
    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clienteRepositoryJpa.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public List<ListClientResponse> findActiveClients() {
        return clientMapper.toListDtoList(clienteRepositoryJpa.findActiveClients().orElse(null));
    }
}
