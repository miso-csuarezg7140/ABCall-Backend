package com.abcall.agentes.domain.service;

import com.abcall.agentes.domain.dto.response.ResponseServiceDto;

public interface IAgentService {

    ResponseServiceDto authenticate(String documentType, String documentNumber, String password);

    ResponseServiceDto documentTypeList();
}
