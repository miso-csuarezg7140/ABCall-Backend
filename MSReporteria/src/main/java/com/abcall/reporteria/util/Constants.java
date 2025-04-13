package com.abcall.reporteria.util;

import java.time.LocalDateTime;

public class Constants {

    public static final String PAGINATION_VALUE = "paginacion";
    public static final String RESPONSE_VALUE = "info";
    public static final LocalDateTime HOY = LocalDateTime.now();

    Constants() {
        throw new IllegalStateException("Utility class");
    }
}