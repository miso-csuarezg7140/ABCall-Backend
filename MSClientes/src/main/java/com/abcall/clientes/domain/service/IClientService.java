package com.abcall.clientes.domain.service;

import com.abcall.clientes.domain.dto.response.ResponseServiceDto;

public interface IClientService {

    ResponseServiceDto authenticateClient(String username, String password);
}
