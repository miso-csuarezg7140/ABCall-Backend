package com.abcall.clientes.util;

import com.abcall.clientes.domain.dto.ResponseServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiUtils {

    /**
     * Construir Respuesta Servicio
     *
     * @param statusCode        el codigo de respuesta
     * @param statusDescription el mensaje de respuesta
     * @param data              información adicional de la respuesta
     * @return ResponseServiceDto con la información de respuesta
     */
    public static ResponseServiceDto buildResponseServiceDto(String statusCode,
                                                             String statusDescription,
                                                             Object data) {
        return ResponseServiceDto.builder()
                .statusCode(statusCode)
                .statusDescription(statusDescription)
                .data(data)
                .build();
    }
}
