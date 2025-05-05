package com.abcall.auth.util;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final String ROLES = "roles";
    public static final String USER_TYPE = "userType";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT = "client";
    public static final String AGENT = "agent";
    public static final String REFRESH = "refresh";
    public static final String DOCUMENT_TYPE = "documentType";
    public static final String DOCUMENT_NUMBER = "documentNumber";
    public static final String VALOR_DEFECTO_TIPO_DOCUMENTO = "1";
    public static final String VALIDACION_TIPO_USUARIO = "^(cliente|agente)$";
    public static final String VALIDACION_DOCUMENTO = "^[A-Z0-9]{5,10}$";
    public static final String VALIDACION_TIPO_DOCUMENTO = "^[1-6]$";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}