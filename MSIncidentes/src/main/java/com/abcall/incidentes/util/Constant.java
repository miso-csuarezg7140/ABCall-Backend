package com.abcall.incidentes.util;

import java.time.LocalDateTime;

public class Constant {

    public static final String CODIGO_200 = "200";
    public static final String MENSAJE_200 = "Transacción exitosa.";
    public static final String CODIGO_201 = "201";
    public static final String MENSAJE_201 = "Recurso creado correctamente.";
    public static final String CODIGO_206 = "206";
    public static final String MENSAJE_206 = "No se encuentran resultados con los parámetros suministrados.";
    public static final String CODIGO_400 = "400";
    public static final String MENSAJE_400 = "Petición incorrecta. Valide los parámetros de entrada.";
    public static final String CODIGO_500 = "500";
    public static final String MENSAJE_500 = "Error interno del servidor";
    public static final LocalDateTime HOY = LocalDateTime.now();
    public static final String VALIDACION_NUMERICO = "^\\d+$";

    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
