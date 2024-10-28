package com.abcall.clientes.domain.service;

import com.abcall.clientes.domain.dto.ClienteDto;
import com.abcall.clientes.domain.dto.ResponseServiceDto;

public interface ClienteService {

    ResponseServiceDto registrar(ClienteDto clienteDto);

    ResponseServiceDto login(String numDocumentoCliente, String contrasena);
}
