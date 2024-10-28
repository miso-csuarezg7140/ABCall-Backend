package com.abcall.clientes.persistence.repository;

import com.abcall.clientes.persistence.entity.Cliente;

public interface ClienteRepository {

    Cliente obtenerPorId(Long numDocumentoCliente);

    Cliente guardar(Cliente cliente);
}
