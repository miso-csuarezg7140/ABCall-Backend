package com.abcall.incidentes.util;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final String VALIDACION_NUMERICO = "^\\d+$";
    public static final String ESTADO_DEFAULT = "TODOS";
    public static final String USUARIO_DEFAULT = "USUARIO";
    public static final String VALIDACION_TIPO_DOC_USUARIO = "^[1-6]$";
    public static final String VALIDACION_DOCUMENTO_CLIENTE = "^[0-9]{9,10}$";
    public static final String VALIDACION_DOCUMENTO_USUARIO = "^[A-Z0-9]{5,10}$";
    public static final String VALIDACION_FECHA = "^(19[0-9]{2}|20[0-9]{2}|2100)/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$";
    public static final String VALIDACION_3_DIGITOS = "^[1-9][0-9]{2}$";
    public static final String VALIDACION_ESTADO = "^(ACTIVO|EN PROCESO|CERRADO|TODOS)$";

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}
