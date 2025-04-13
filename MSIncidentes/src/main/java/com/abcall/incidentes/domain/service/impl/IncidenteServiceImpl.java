package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.mappers.IncidenteMapper;
import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenteServiceImpl implements IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final IncidenteMapper incidenteMapper;
    private final ApiUtils apiUtils;

    @Override
    public ResponseServiceDto consultar(String tipoDocUsuario, String numeroDocUsuarioStr) {
        try {
            Long numeroDocUsuario = Long.valueOf(numeroDocUsuarioStr);
            List<IncidenteDto> incidenteDtoList = incidenteMapper.toDtoList(
                    incidenteRepository.obtenerPorUsuario(tipoDocUsuario, numeroDocUsuario));

            if (incidenteDtoList != null && !incidenteDtoList.isEmpty()) {
                return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(), HttpResponseMessages.OK.getMessage(),
                        incidenteDtoList);
            } else {
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
            }
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }

    @Override
    public ResponseServiceDto crear(IncidenteDto incidenteDto) {
        try {
            Incidente incidente = incidenteMapper.toEntity(incidenteDto);
            IncidenteDto incidenteCreado = incidenteMapper.toDto(incidenteRepository.crear(incidente));
            return apiUtils.buildResponse(HttpResponseCodes.CREATED.getCode(),
                    HttpResponseMessages.CREATED.getMessage(), incidenteCreado);
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }
}
