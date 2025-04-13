package com.abcall.ia.domain.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResponseServiceDtoTest {

    @Test
    void responseServiceDtoShouldStoreAllFields() {
        ResponseServiceDto dto = new ResponseServiceDto(200, "Consulta exitosa.", "data");
        assertEquals(200, dto.getStatusCode());
        assertEquals("Consulta exitosa.", dto.getStatusDescription());
        assertEquals("data", dto.getData());
    }

    @Test
    void responseServiceDtoShouldHandleNullFields() {
        ResponseServiceDto dto = new ResponseServiceDto(null, null, null);
        assertNull(dto.getStatusCode());
        assertNull(dto.getStatusDescription());
        assertNull(dto.getData());
    }

    @Test
    void responseServiceDtoShouldHandleEmptyStatusDescription() {
        ResponseServiceDto dto = new ResponseServiceDto(200, "", "data");
        assertEquals(200, dto.getStatusCode());
        assertEquals("", dto.getStatusDescription());
        assertEquals("data", dto.getData());
    }

    @Test
    void responseServiceDtoShouldHandleEmptyData() {
        ResponseServiceDto dto = new ResponseServiceDto(200, "Consulta exitosa.", "");
        assertEquals(200, dto.getStatusCode());
        assertEquals("Consulta exitosa.", dto.getStatusDescription());
        assertEquals("", dto.getData());
    }
}