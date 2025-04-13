package com.abcall.clientes.persistence.repository;

import com.abcall.clientes.domain.dto.ClientDto;

public interface IClientRepository {

    ClientDto findByDocumentNumber(Long documentNumber);

    ClientDto save(ClientDto client);
}
