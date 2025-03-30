package com.abcall.agentes.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ConstantTest {

    @Test
    void constructor_CuandoSeIntentaInstanciar_LanzaExcepcion() {
        // Assert
        assertThrows(IllegalStateException.class, Constant::new, "Utility class");
    }

    @Test
    void codigosHTTP_DebeTenerValoresCorrectos() {
        // Assert
        assertEquals("200", Constant.CODIGO_200);
        assertEquals("201", Constant.CODIGO_201);
        assertEquals("206", Constant.CODIGO_206);
        assertEquals("400", Constant.CODIGO_400);
        assertEquals("401", Constant.CODIGO_401);
        assertEquals("500", Constant.CODIGO_500);
    }

    @Test
    void mensajesHTTP_DebeTenerValoresCorrectos() {
        // Assert
        assertEquals("Transacción exitosa.", Constant.MENSAJE_200);
        assertEquals("Recurso creado correctamente.", Constant.MENSAJE_201);
        assertEquals("No se encuentran resultados con los parámetros suministrados.", Constant.MENSAJE_206);
        assertEquals("Petición incorrecta. Valide los parámetros de entrada.", Constant.MENSAJE_400);
        assertEquals("Contraseña incorrecta.", Constant.MENSAJE_401);
        assertEquals("Error interno del servidor", Constant.MENSAJE_500);
    }

    @Test
    void validacionContrasena_DebeValidarCorrectamente() {
        Pattern pattern = Pattern.compile(Constant.VALIDACION_CONTRASENA);

        // Contraseñas válidas
        assertTrue(pattern.matcher("Abc123#$").matches());
        assertTrue(pattern.matcher("Password1!").matches());

        // Contraseñas inválidas
        assertFalse(pattern.matcher("abc123").matches()); // Sin mayúscula y caracteres especiales
        assertFalse(pattern.matcher("Abcd123").matches()); // Sin caracteres especiales
        assertFalse(pattern.matcher("abc#$%").matches()); // Sin mayúscula y números
        assertFalse(pattern.matcher("Ab1!").matches()); // Muy corta
    }

    @Test
    void validacionNumerico_DebeValidarCorrectamente() {
        Pattern pattern = Pattern.compile(Constant.VALIDACION_NUMERICO);

        // Valores válidos
        assertTrue(pattern.matcher("123").matches());
        assertTrue(pattern.matcher("0").matches());
        assertTrue(pattern.matcher("9999999").matches());

        // Valores inválidos
        assertFalse(pattern.matcher("abc").matches());
        assertFalse(pattern.matcher("123a").matches());
        assertFalse(pattern.matcher("12.3").matches());
        assertFalse(pattern.matcher("-123").matches());
        assertFalse(pattern.matcher("").matches());
    }

    @Test
    void hoy_NoDebeSerNull() {
        // Assert
        assertNotNull(Constant.HOY);
        assertTrue(Constant.HOY instanceof LocalDateTime);
    }

    @Test
    void hoy_DebeSerFechaValida() {
        // Arrange
        LocalDateTime ahora = LocalDateTime.now();

        // Assert
        // Verificamos que HOY no sea una fecha futura
        assertTrue(Constant.HOY.isBefore(ahora.plusSeconds(1)));
        // Verificamos que HOY no sea una fecha muy antigua
        assertTrue(Constant.HOY.isAfter(ahora.minusMinutes(1)));
    }
}
