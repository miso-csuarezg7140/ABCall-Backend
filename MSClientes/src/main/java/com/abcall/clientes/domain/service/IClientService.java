package com.abcall.clientes.domain.service;

import com.abcall.clientes.domain.dto.request.ClientRegisterRequest;
import com.abcall.clientes.domain.dto.response.ResponseServiceDto;

public interface IClientService {

    ResponseServiceDto authenticate(String documentClient, String password);

    ResponseServiceDto register(ClientRegisterRequest clientRegisterRequest);

    ResponseServiceDto validateUser(String documentClient, String documentTypeUser, String documentUser);

    ResponseServiceDto list();

    ResponseServiceDto documentTypeList();
}
