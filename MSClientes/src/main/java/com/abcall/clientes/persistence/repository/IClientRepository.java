package com.abcall.clientes.persistence.repository;

import com.abcall.clientes.domain.dto.ClientDto;
import com.abcall.clientes.domain.dto.response.ListClientResponse;

import java.util.List;

public interface IClientRepository {

    ClientDto findByDocumentNumber(Long documentNumber);

    ClientDto save(ClientDto client);

    List<ListClientResponse> findActiveClients();
}
