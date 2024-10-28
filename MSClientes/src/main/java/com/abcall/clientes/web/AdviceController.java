package com.abcall.clientes.web;

import com.abcall.clientes.domain.dto.ResponseServiceDto;
import com.abcall.clientes.exception.ApiException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.abcall.clientes.util.ApiUtils.buildResponseServiceDto;
import static com.abcall.clientes.util.Constant.CODIGO_400;
import static com.abcall.clientes.util.Constant.MENSAJE_400;

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
        ResponseServiceDto responseServiceDto = buildResponseServiceDto(apiException.getCode(),
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
        ResponseServiceDto responseServiceDto = buildResponseServiceDto("400",
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
        return new ResponseEntity<>(buildResponseServiceDto("500",
                exception.getMessage(),
                exception.getStackTrace()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para MethodArgumentNotValidException y ConstraintViolationException.
     * <p>
     * Este metodo maneja las excepciones lanzadas cuando los argumentos del metodo no son válidos
     * o cuando hay violaciones de restricciones. Recopila los errores de validación y los devuelve
     * en un ResponseServiceDto con un estado BAD_REQUEST.
     * </p>
     *
     * @param ex la excepción lanzada cuando los argumentos del metodo no son válidos o hay violaciones de restricciones
     * @return ResponseEntity con un ResponseServiceDto que contiene los detalles de los errores de validación
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseServiceDto> handleValidationExcpetions(Exception ex) {
        List<String> errors = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().forEach(error ->
                    errors.add(error.getDefaultMessage())
            );
        } else if (ex instanceof ConstraintViolationException) {
            ((ConstraintViolationException) ex).getConstraintViolations().forEach(violation ->
                    errors.add(violation.getMessage())
            );
        }

        ResponseServiceDto response = buildResponseServiceDto(CODIGO_400, MENSAJE_400, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}