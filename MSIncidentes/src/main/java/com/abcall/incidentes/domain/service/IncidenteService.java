package com.abcall.incidentes.domain.service;

import com.abcall.incidentes.domain.dto.request.ActualizarIncidenteRequest;
import com.abcall.incidentes.domain.dto.request.CrearIncidenteRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;

public interface IncidenteService {

    ResponseServiceDto consultar(String tipoDocUsuario, String numeroDocUsuarioStr);

    ResponseServiceDto consultarDetalle(String idIncidenteStr);

    ResponseServiceDto crear(CrearIncidenteRequest crearIncidenteRequest);

    ResponseServiceDto actualizar(ActualizarIncidenteRequest actualizarIncidenteRequest);
}
