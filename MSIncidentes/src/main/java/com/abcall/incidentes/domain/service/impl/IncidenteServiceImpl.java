package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.domain.dto.IncidenteDto;
import com.abcall.incidentes.domain.dto.ResponseServiceDto;
import com.abcall.incidentes.domain.service.IncidenteService;
import com.abcall.incidentes.persistence.entity.Incidente;
import com.abcall.incidentes.persistence.mappers.IncidenteMapper;
import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.abcall.incidentes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.incidentes.util.Constant.CODIGO_200;
import static com.abcall.incidentes.util.Constant.CODIGO_201;
import static com.abcall.incidentes.util.Constant.CODIGO_206;
import static com.abcall.incidentes.util.Constant.CODIGO_500;
import static com.abcall.incidentes.util.Constant.MENSAJE_200;
import static com.abcall.incidentes.util.Constant.MENSAJE_201;
import static com.abcall.incidentes.util.Constant.MENSAJE_206;
import static com.abcall.incidentes.util.Constant.MENSAJE_500;

@Service
@RequiredArgsConstructor
public class IncidenteServiceImpl implements IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final IncidenteMapper incidenteMapper;

    @Override
    public ResponseServiceDto consultar(String tipoDocUsuario, String numeroDocUsuarioStr) {
        try {
            Long numeroDocUsuario = Long.valueOf(numeroDocUsuarioStr);
            List<IncidenteDto> incidenteDtoList = incidenteMapper.toDtoList(
                    incidenteRepository.obtenerPorUsuario(tipoDocUsuario, numeroDocUsuario));

            if (incidenteDtoList != null && !incidenteDtoList.isEmpty()) {
                return buildResponseServiceDto(CODIGO_200, MENSAJE_200, incidenteDtoList);
            } else {
                return buildResponseServiceDto(CODIGO_206, MENSAJE_206, new HashMap<>());
            }
        } catch (Exception ex) {
            return buildResponseServiceDto(CODIGO_500, MENSAJE_500, ex.getMessage());
        }
    }

    @Override
    public ResponseServiceDto crear(IncidenteDto incidenteDto) {
        try {
            Incidente incidente = incidenteRepository.crear(incidenteMapper.toEntity(incidenteDto));
            return buildResponseServiceDto(CODIGO_201, MENSAJE_201, incidente);
        } catch (Exception ex) {
            return buildResponseServiceDto(CODIGO_500, MENSAJE_500, ex.getMessage());
        }
    }
}
