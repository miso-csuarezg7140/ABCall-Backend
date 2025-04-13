package com.abcall.agentes.util;

import java.time.LocalDateTime;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final LocalDateTime HOY = LocalDateTime.now();
    public static final String VALIDACION_CONTRASENA = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
    public static final String VALIDACION_NUMERICO = "^\\d+$";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}
