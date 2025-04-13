package com.abcall.incidentes.util;

import java.time.LocalDateTime;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final LocalDateTime HOY = LocalDateTime.now();
    public static final String VALIDACION_NUMERICO = "^\\d+$";
    public static final String ESTADO_DEFAULT = "ACTIVO";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}
