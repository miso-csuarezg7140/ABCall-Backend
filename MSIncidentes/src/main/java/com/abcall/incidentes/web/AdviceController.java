package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.dto.response.ResponseServiceDto;
import com.abcall.incidentes.exception.ApiException;
import com.abcall.incidentes.util.ApiUtils;
import com.abcall.incidentes.util.enums.HttpResponseCodes;
import com.abcall.incidentes.util.enums.HttpResponseMessages;
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

@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceController {

    private final ApiUtils apiUtils;

    /**
     * Handles ApiException thrown by the service.
     * <p>
     * This method handles ApiException and constructs a ResponseServiceDto with the exception details.
     * It returns a ResponseEntity with the appropriate HTTP status and the response data.
     * </p>
     *
     * @param apiException the ApiException that was thrown
     * @return ResponseEntity with a ResponseServiceDto containing the exception details
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResponseServiceDto> handlerApiException(ApiException apiException) {
        ResponseServiceDto responseDto = apiUtils.buildResponse(apiException.getCode(),
                apiException.getMessage(),
                apiException.getAdditionalExceptionMessage());
        return new ResponseEntity<>(responseDto,
                apiException.getHttpStatus());
    }

    /**
     * Handler for BindException to manage validation errors.
     * <p>
     * This method handles BindException thrown during validation and extracts the first error message
     * to return in a ResponseServiceDto with a BAD_REQUEST status.
     * </p>
     *
     * @param exception the BindException that was thrown
     * @return ResponseEntity with a ResponseServiceDto containing the validation error message
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ResponseServiceDto> handlerBindException(BindException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String finalMessage = allErrors.getFirst().getDefaultMessage();
        ResponseServiceDto responseDto = apiUtils.buildResponse(400, finalMessage, List.of());
        return new ResponseEntity<>(responseDto,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exceptions that are not specifically caught by other exception handlers.
     * <p>
     * This method catches any exception that is not handled by other specific exception handlers
     * and returns a ResponseEntity with an INTERNAL_SERVER_ERROR status and the exception details.
     * </p>
     *
     * @param exception the exception that was thrown
     * @return ResponseEntity with a ResponseServiceDto containing the exception message and stack trace
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseServiceDto> handlerException(Exception exception) {
        return new ResponseEntity<>(apiUtils.buildResponse(
                500, exception.getMessage(), exception.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation exceptions for method arguments and constraint violations.
     * <p>
     * This method handles exceptions thrown when method arguments are not valid
     * or when there are constraint violations. It collects validation errors and returns
     * them in a ResponseServiceDto with a BAD_REQUEST status.
     * </p>
     *
     * @param ex the exception thrown when method arguments are not valid or there are constraint violations
     * @return ResponseEntity with a ResponseServiceDto containing the validation error details
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ResponseServiceDto> handleValidationExceptions(Exception ex) {
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

        ResponseServiceDto response = apiUtils.buildResponse(HttpResponseCodes.BAD_REQUEST.getCode(),
                HttpResponseMessages.BAD_REQUEST.getMessage(), errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}