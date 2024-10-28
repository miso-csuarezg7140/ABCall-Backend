package com.abcall.agentes.domain.service;

import com.abcall.agentes.domain.dto.ResponseServiceDto;

public interface AgenteService {

    ResponseServiceDto login(String tipoDocumentoAgente, String numDocumentoAgente, String contrasena);
}
