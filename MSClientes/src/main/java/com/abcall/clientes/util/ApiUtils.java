package com.abcall.clientes.util;

import com.abcall.clientes.domain.dto.ResponseServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    /**
     * Codifica una cadena de texto a formato Base64.
     * Este método convierte una cadena de texto plano a su representación en Base64.
     *
     * @param password La cadena que se desea codificar en Base64.
     *                 No debe ser null ni estar vacía.
     * @return La cadena codificada en formato Base64.
     */
    public static String encodeToBase64(String password) {
        try {
            byte[] encodedBytes = Base64.getEncoder().encode(
                    password.getBytes(StandardCharsets.UTF_8)
            );
            return new String(encodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al codificar la contraseña", e);
        }
    }

    /**
     * Decodifica una cadena en formato Base64 a su valor original.
     * Este método convierte una cadena codificada en Base64 a su representación original en texto plano.
     *
     * @param encodedPassword La cadena codificada en Base64 que se desea decodificar.
     *                        No debe ser null ni estar vacía.
     * @return La cadena decodificada en su formato original.
     */
    public static String decodeFromBase64(String encodedPassword) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(
                    encodedPassword.getBytes(StandardCharsets.UTF_8)
            );
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al decodificar la contraseña", e);
        }
    }
}
