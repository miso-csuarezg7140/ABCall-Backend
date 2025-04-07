package com.abcall.clientes.util;

import java.time.LocalDateTime;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final LocalDateTime HOY = LocalDateTime.now();
    public static final Character ESTADO_DEFAULT = 'A';
    public static final String VALIDACION_ESTADOS = "^[A|I|R]$";
    public static final String VALIDACION_NUMERICO = "^\\d{9,10}$";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}