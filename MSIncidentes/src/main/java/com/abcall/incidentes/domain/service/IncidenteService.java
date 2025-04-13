package com.abcall.incidentes.domain.service;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;

public interface IncidenteService {

    ResponseServiceDto consultar(String tipoDocUsuario, String numeroDocUsuarioStr);

    ResponseServiceDto crear(IncidenteDto incidenteDto);
}
