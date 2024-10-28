package com.abcall.clientes.domain.service.impl;

import com.abcall.clientes.domain.dto.ClienteDto;
import com.abcall.clientes.domain.dto.ResponseServiceDto;
import com.abcall.clientes.domain.service.ClienteService;
import com.abcall.clientes.persistence.entity.Cliente;
import com.abcall.clientes.persistence.mappers.ClienteMapper;
import com.abcall.clientes.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.abcall.clientes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.clientes.util.ApiUtils.decodeFromBase64;
import static com.abcall.clientes.util.ApiUtils.encodeToBase64;
import static com.abcall.clientes.util.Constant.CODIGO_200;
import static com.abcall.clientes.util.Constant.CODIGO_201;
import static com.abcall.clientes.util.Constant.CODIGO_206;
import static com.abcall.clientes.util.Constant.CODIGO_401;
import static com.abcall.clientes.util.Constant.CODIGO_409;
import static com.abcall.clientes.util.Constant.CODIGO_500;
import static com.abcall.clientes.util.Constant.MENSAJE_200;
import static com.abcall.clientes.util.Constant.MENSAJE_201;
import static com.abcall.clientes.util.Constant.MENSAJE_206;
import static com.abcall.clientes.util.Constant.MENSAJE_401;
import static com.abcall.clientes.util.Constant.MENSAJE_409;
import static com.abcall.clientes.util.Constant.MENSAJE_500;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ResponseServiceDto registrar(ClienteDto clienteDto) {
        try {
            ClienteDto clienteExistente = clienteMapper.toDto(
                    clienteRepository.obtenerPorId(clienteDto.getNumeroDocumento()));

            if (null == clienteExistente) {
                String passwordEncoded = encodeToBase64(clienteDto.getContrasena());
                clienteDto.setContrasena(passwordEncoded);
                Cliente cliente = clienteMapper.toEntity(clienteDto);
                clienteDto = clienteMapper.toDto(clienteRepository.guardar(cliente));
                return buildResponseServiceDto(CODIGO_201, MENSAJE_201, clienteDto);
            } else
                return buildResponseServiceDto(CODIGO_409, MENSAJE_409, new HashMap<>());

        } catch (Exception ex) {
            return buildResponseServiceDto(CODIGO_500, MENSAJE_500, ex.getMessage());
        }
    }

    @Override
    public ResponseServiceDto login(String numDocumentoCliente, String contrasena) {

        try {
            ClienteDto clienteDto = clienteMapper.toDto(
                    clienteRepository.obtenerPorId(Long.valueOf(numDocumentoCliente)));

            if (null != clienteDto) {
                String passwordDecoded = decodeFromBase64(clienteDto.getContrasena());
                if (passwordDecoded.equals(contrasena))
                    return buildResponseServiceDto(CODIGO_200, MENSAJE_200, clienteDto);
                else
                    return buildResponseServiceDto(CODIGO_401, MENSAJE_401, new HashMap<>());
            } else
                return buildResponseServiceDto(CODIGO_206, MENSAJE_206, new HashMap<>());
        } catch (Exception ex) {
            return buildResponseServiceDto(CODIGO_500, MENSAJE_500, ex.getMessage());
        }
    }
}
