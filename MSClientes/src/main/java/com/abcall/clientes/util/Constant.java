package com.abcall.clientes.util;

public class Constant {

    public static final String CODIGO_200 = "200";
    public static final String MENSAJE_200 = "Transacción exitosa.";
    public static final String CODIGO_400 = "400";
    public static final String MENSAJE_400 = "Petición incorrecta. Valide los parámetros de entrada.";
    public static final String CODIGO_500 = "500";
    public static final String MENSAJE_500 = "Error interno del servidor";

    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
