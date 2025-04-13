package com.abcall.incidentes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Change for autogenerate

    private final int code;

    private final HttpStatus httpStatus;

    private final String message;

    private final List<String> additionalExceptionMessage;
}
