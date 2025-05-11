package com.abcall.incidentes.domain.service;

import com.abcall.incidentes.domain.dto.request.ConsultIncidentRequest;
import com.abcall.incidentes.domain.dto.request.CreateIncidentRequest;
import com.abcall.incidentes.domain.dto.request.UpdateIncidentRequest;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;

public interface IIncidentService {

    ResponseServiceDto consultar(ConsultIncidentRequest consultIncidentRequest);

    ResponseServiceDto consultarDetalle(String idIncidenteStr);

    ResponseServiceDto crear(CreateIncidentRequest createIncidentRequest);

    ResponseServiceDto actualizar(UpdateIncidentRequest updateIncidentRequest);
}
