package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.persistence.entity.Cliente;
import com.abcall.clientes.persistence.repository.ClienteRepository;
import com.abcall.clientes.persistence.repository.jpa.ClienteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Override
    public Cliente obtenerPorId(Long numDocumentoCliente) {
        return clienteRepositoryJpa.findById(numDocumentoCliente).orElse(null);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepositoryJpa.save(cliente);
    }
}
