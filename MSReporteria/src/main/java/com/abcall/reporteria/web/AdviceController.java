package com.abcall.reporteria.web;

import com.abcall.reporteria.domain.dto.ResponseServiceDto;
import com.abcall.reporteria.exception.ApiException;
import com.abcall.reporteria.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceController {

    /**
     * Manejo de excepciones ApiException del servicio
     *
     * @param apiException la excepcion ApiException
     * @return ResponseEntity con la respuesta definida al servicio
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResponseServiceDto> handlerApiException(ApiException apiException) {
        ResponseServiceDto responseServiceDto = ApiUtils.buildResponseServiceDto(apiException.getCode(),
                apiException.getMessage(),
                apiException.getAdditionalExceptionMessage());
        return new ResponseEntity<>(responseServiceDto,
                apiException.getHttpStatus());
    }

    /**
     * Handler BindException manejo de validaciones
     *
     * @param exception la excepcion
     * @return Respuesta con mensaje de validacion
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ResponseServiceDto> handlerBindException(BindException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String finalMessage = allErrors.get(0).getDefaultMessage();
        ResponseServiceDto responseServiceDto = ApiUtils.buildResponseServiceDto("400",
                finalMessage,
                List.of());
        return new ResponseEntity<>(responseServiceDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejo de excepciones Exception del servicio
     *
     * @param exception la excepcion Exception
     * @return ResponseEntity con la respuesta definida al servicio
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseServiceDto> handlerException(Exception exception) {
        return new ResponseEntity<>(ApiUtils.buildResponseServiceDto("500",
                exception.getMessage(),
                exception.getStackTrace()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}