package com.abcall.reporteria.util.enums;

public enum HttpResponseMessages {

    OK("Consulta exitosa."),
    CREATED("Registro exitoso."),
    ACCEPTED("Actualización exitosa."),
    NO_CONTENT("La consulta no retorna registros con los valores suministrados."),
    BUSINESS_MISTAKE("Error de negocio."),
    BAD_REQUEST("Valores nulos o incorrectos en los parámetros de entrada."),
    UNAUTHORIZED("Autenticación requerida."),
    FORBIDDEN("Acceso denegado."),
    NOT_FOUND("Recurso no encontrado."),
    METHOD_NOT_ALLOWED("Método no permitido para este recurso."),
    INTERNAL_SERVER_ERROR("Error interno del servidor."),
    NOT_IMPLEMENTED("Funcionalidad no implementada."),
    SERVICE_UNAVAILABLE("Servicio temporalmente no disponible.");

    private final String message;

    HttpResponseMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}