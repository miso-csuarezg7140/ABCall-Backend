package com.abcall.incidentes.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ConstantsTest {

    @Test
    void constructorThrowsExceptionWhenInvoked() {
        Exception exception = assertThrows(IllegalStateException.class, Constants::new);
        assertEquals("Utility class", exception.getMessage());
    }

    @Test
    void validacionNumericoMatchesOnlyDigits() {
        assertTrue("12345".matches(Constants.VALIDACION_NUMERICO));
        assertFalse("123a5".matches(Constants.VALIDACION_NUMERICO));
    }

    @Test
    void validacionTipoDocUsuarioMatchesValidRange() {
        assertTrue("1".matches(Constants.VALIDACION_TIPO_DOC_USUARIO));
        assertTrue("6".matches(Constants.VALIDACION_TIPO_DOC_USUARIO));
        assertFalse("0".matches(Constants.VALIDACION_TIPO_DOC_USUARIO));
        assertFalse("7".matches(Constants.VALIDACION_TIPO_DOC_USUARIO));
    }

    @Test
    void validacionDocumentoClienteMatchesValidLength() {
        assertTrue("123456789".matches(Constants.VALIDACION_DOCUMENTO_CLIENTE));
        assertTrue("1234567890".matches(Constants.VALIDACION_DOCUMENTO_CLIENTE));
        assertFalse("12345678".matches(Constants.VALIDACION_DOCUMENTO_CLIENTE));
        assertFalse("12345678901".matches(Constants.VALIDACION_DOCUMENTO_CLIENTE));
    }

    @Test
    void validacionDocumentoUsuarioMatchesValidPattern() {
        assertTrue("ABCDE12345".matches(Constants.VALIDACION_DOCUMENTO_USUARIO));
        assertFalse("123".matches(Constants.VALIDACION_DOCUMENTO_USUARIO));
        assertFalse("abcde12345".matches(Constants.VALIDACION_DOCUMENTO_USUARIO));
    }

    @Test
    void validacionFechaMatchesValidDateFormat() {
        assertTrue("2023/12/31".matches(Constants.VALIDACION_FECHA));
        assertFalse("2023-12-31".matches(Constants.VALIDACION_FECHA));
        assertFalse("2023/13/01".matches(Constants.VALIDACION_FECHA));
    }

    @Test
    void validacion3DigitosMatchesValidRange() {
        assertTrue("1".matches(Constants.VALIDACION_3_DIGITOS));
        assertTrue("123".matches(Constants.VALIDACION_3_DIGITOS));
        assertFalse("012".matches(Constants.VALIDACION_3_DIGITOS));
        assertFalse("1234".matches(Constants.VALIDACION_3_DIGITOS));
    }

    @Test
    void validacionEstadoMatchesValidStates() {
        assertTrue("ACTIVO".matches(Constants.VALIDACION_ESTADO));
        assertTrue("EN PROCESO".matches(Constants.VALIDACION_ESTADO));
        assertTrue("CERRADO".matches(Constants.VALIDACION_ESTADO));
        assertTrue("TODOS".matches(Constants.VALIDACION_ESTADO));
        assertFalse("INACTIVO".matches(Constants.VALIDACION_ESTADO));
    }
}