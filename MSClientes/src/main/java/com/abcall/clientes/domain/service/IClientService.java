package com.abcall.clientes.domain.service;

import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;

public interface IClientService {

    ResponseServiceDto authenticateClient(String username, String password);

    ResponseServiceDto registerClient(ClientRegisterRequest clientRegisterRequest);

    ResponseServiceDto validateUserClient(String documentClient, String documentTypeUser, String documentUser);

    ResponseServiceDto listarClientes();
}
