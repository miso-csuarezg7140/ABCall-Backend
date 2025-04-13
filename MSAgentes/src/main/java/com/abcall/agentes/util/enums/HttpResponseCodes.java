package com.abcall.agentes.util.enums;

public enum HttpResponseCodes {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BUSINESS_MISTAKE(206),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    SERVICE_UNAVAILABLE(503);

    private final int code;

    HttpResponseCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}