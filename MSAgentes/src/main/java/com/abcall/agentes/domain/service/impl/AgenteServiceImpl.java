package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.dto.AgenteDto;
import com.abcall.agentes.domain.dto.response.ResponseServiceDto;
import com.abcall.agentes.domain.service.AgenteService;
import com.abcall.agentes.persistence.entity.Agente;
import com.abcall.agentes.persistence.entity.compositekey.AgentePK;
import com.abcall.agentes.persistence.mappers.AgenteMapper;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import com.abcall.agentes.util.ApiUtils;
import com.abcall.agentes.util.enums.HttpResponseCodes;
import com.abcall.agentes.util.enums.HttpResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.abcall.agentes.util.ApiUtils.decodeFromBase64;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl implements AgenteService {

    private final AgenteRepository agenteRepository;
    private final AgenteMapper agenteMapper;
    private final ApiUtils apiUtils;

    @Override
    public ResponseServiceDto login(String tipoDocumentoAgente, String numDocumentoAgenteStr, String contrasena) {

        try {
            AgentePK agentePK = new AgentePK();
            agentePK.setTipoDocumento(tipoDocumentoAgente);
            Long numDocumentoAgente = Long.valueOf(numDocumentoAgenteStr);
            agentePK.setNumeroDocumento(numDocumentoAgente);
            Agente agente = agenteRepository.obtenerPorId(agentePK);
            AgenteDto agenteDto = agenteMapper.toDto(agente);

            if (null != agenteDto) {
                String passwordDecoded = decodeFromBase64(agenteDto.getContrasena());
                if (passwordDecoded.equals(contrasena)) {
                    agenteDto.setContrasena(passwordDecoded);
                    return apiUtils.buildResponse(HttpResponseCodes.OK.getCode(),
                            HttpResponseMessages.OK.getMessage(), agenteDto);
                } else
                    return apiUtils.buildResponse(HttpResponseCodes.UNAUTHORIZED.getCode(),
                            HttpResponseMessages.UNAUTHORIZED.getMessage(), new HashMap<>());
            } else
                return apiUtils.buildResponse(HttpResponseCodes.BUSINESS_MISTAKE.getCode(),
                        HttpResponseMessages.NO_CONTENT.getMessage(), new HashMap<>());
        } catch (Exception ex) {
            return apiUtils.buildResponse(HttpResponseCodes.INTERNAL_SERVER_ERROR.getCode(),
                    HttpResponseMessages.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());
        }
    }
}
