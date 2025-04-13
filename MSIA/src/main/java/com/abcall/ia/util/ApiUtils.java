package com.abcall.ia.util;

import com.abcall.ia.domain.dto.response.ResponseServiceDto;
import com.abcall.ia.util.enums.HttpResponseCodes;
import com.abcall.ia.util.enums.HttpResponseMessages;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApiUtils {

    /**
     * Constructs a ResponseServiceDto for a Bad Request response.
     *
     * @param bindingResult the validation result containing field errors
     * @return a ResponseServiceDto instance with the Bad Request status code and validation error messages
     */
    public ResponseServiceDto badRequestResponse(BindingResult bindingResult) {
        Map<String, String> validationErrors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (error1, error2) -> error1
                ));

        return buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(), HttpResponseMessages.BAD_REQUEST.getMessage(),
                validationErrors);
    }

    /**
     * Constructs a ResponseServiceDto with the given status code, status description, response data, and pagination information.
     *
     * @param statusCode        the response status code
     * @param statusDescription the response status description
     * @param response          additional response information
     * @param pagination        pagination information
     * @return a ResponseServiceDto containing the response information and pagination data if the status code is OK,
     * otherwise a ResponseServiceDto containing the response information only
     */
    public ResponseServiceDto buildResponse(int statusCode,
                                            String statusDescription,
                                            Object response, Object pagination) {

        Map<String, Object> data = new HashMap<>();
        if (HttpResponseCodes.OK.getCode() == statusCode) {
            data.put(Constants.RESPONSE_VALUE, response);
            data.put(Constants.PAGINATION_VALUE, pagination);

            return ResponseServiceDto.builder()
                    .statusCode(statusCode)
                    .statusDescription(statusDescription)
                    .data(data)
                    .build();
        } else
            return ResponseServiceDto.builder()
                    .statusCode(statusCode)
                    .statusDescription(statusDescription)
                    .data(response)
                    .build();
    }

    /**
     * Constructs a ResponseServiceDto with the given status code, status description, and response data.
     *
     * @param statusCode        the response status code
     * @param statusDescription the response status description
     * @param response          additional response information
     * @return a ResponseServiceDto containing the response information
     */
    public ResponseServiceDto buildResponse(int statusCode,
                                            String statusDescription,
                                            Object response) {

        return ResponseServiceDto.builder()
                .statusCode(statusCode)
                .statusDescription(statusDescription)
                .data(response)
                .build();
    }
}