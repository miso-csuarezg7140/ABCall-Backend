package com.abcall.clientes.util;

import java.time.LocalDateTime;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final LocalDateTime HOY = LocalDateTime.now();
    public static final Character ESTADO_DEFAULT = 'A';
    public static final String VALIDACION_CONTRASENA = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
    public static final String VALIDACION_DOCUMENTO_CLIENTE = "^[0-9]{9,10}$";
    public static final String VALIDACION_DOCUMENTO_USUARIO = "^[A-Z0-9]{5,10}$";
    public static final String VALIDACION_TIPO_DOCUMENTO = "^[1-6]$";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}