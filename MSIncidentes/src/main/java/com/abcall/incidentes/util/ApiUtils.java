package com.abcall.incidentes.util;

import com.abcall.incidentes.domain.dto.ResponseServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * <p>Handler validation errors.</p>
     *
     * @param bindingResult Validation result.
     * @return Map with field error as key and its description error as value.
     */
    public static Map<String, String> requestHandleErrors(BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>() {
            @Override
            public String toString() {
                StringBuilder stb = new StringBuilder();

                for (Map.Entry<String, String> entry : this.entrySet()) {
                    stb.append(String.format(" Parámetro '%s' %s ", entry.getKey(), entry.getValue()));
                }

                return stb.toString();
            }
        };

        bindingResult.getAllErrors().forEach(error -> {
            String fieldName;
            String errorMessage = error.getDefaultMessage();
            fieldName = ((FieldError) error).getField();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
