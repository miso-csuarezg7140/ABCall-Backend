package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.ResponseServiceDto;
import com.abcall.agentes.domain.service.AgenteService;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.mappers.AgenteMapper;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.abcall.agentes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.agentes.util.ApiUtils.decodeFromBase64;
import static com.abcall.agentes.util.Constant.CODIGO_200;
import static com.abcall.agentes.util.Constant.CODIGO_206;
import static com.abcall.agentes.util.Constant.CODIGO_401;
import static com.abcall.agentes.util.Constant.CODIGO_500;
import static com.abcall.agentes.util.Constant.MENSAJE_200;
import static com.abcall.agentes.util.Constant.MENSAJE_206;
import static com.abcall.agentes.util.Constant.MENSAJE_401;
import static com.abcall.agentes.util.Constant.MENSAJE_500;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl implements AgenteService {

    private final AgenteRepository agenteRepository;
    private final AgenteMapper agenteMapper;

    @Override
    public ResponseServiceDto login(String tipoDocumentoAgente, String numDocumentoAgenteStr, String contrasena) {

        try {
            AgentePK agentePK = new AgentePK();
            agentePK.setTipoDocumento(tipoDocumentoAgente);
            Long numDocumentoAgente = Long.valueOf(numDocumentoAgenteStr);
            agentePK.setNumeroDocumento(numDocumentoAgente);
            AgenteDto agenteDto = agenteMapper.toDto(agenteRepository.obtenerPorId(agentePK));

            if (null != agenteDto) {
                String passwordDecoded = decodeFromBase64(agenteDto.getContrasena());
                if (passwordDecoded.equals(contrasena))
                    return buildResponseServiceDto(CODIGO_200, MENSAJE_200, agenteDto);
                else
                    return buildResponseServiceDto(CODIGO_401, MENSAJE_401, new HashMap<>());
            } else
                return buildResponseServiceDto(CODIGO_206, MENSAJE_206, new HashMap<>());
        } catch (Exception ex) {
            return buildResponseServiceDto(CODIGO_500, MENSAJE_500, ex.getMessage());
        }
    }
}
