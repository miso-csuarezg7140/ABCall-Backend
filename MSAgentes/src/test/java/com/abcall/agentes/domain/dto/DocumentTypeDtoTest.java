package com.abcall.agentes.domain.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class DocumentTypeDtoTest {

    @Test
    void emptyConstructorCreatesObjectWithNullFields() {
        DocumentTypeDto dto = new DocumentTypeDto();

        assertNull(dto.getId());
        assertNull(dto.getCode());
        assertNull(dto.getDescription());
    }

    @Test
    void settersAndGettersWorkCorrectly() {
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(1);
        dto.setCode("DNI");
        dto.setDescription("National ID");

        assertEquals(1, dto.getId());
        assertEquals("DNI", dto.getCode());
        assertEquals("National ID", dto.getDescription());
    }

    @Test
    void fieldsCanBeUpdated() {
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(1);
        dto.setCode("DNI");
        dto.setDescription("National ID");

        dto.setId(2);
        dto.setCode("PASSPORT");
        dto.setDescription("Passport");

        assertEquals(2, dto.getId());
        assertEquals("PASSPORT", dto.getCode());
        assertEquals("Passport", dto.getDescription());
    }

    @Test
    void fieldsCanBeSetToNull() {
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(1);
        dto.setCode("DNI");
        dto.setDescription("National ID");

        dto.setId(null);
        dto.setCode(null);
        dto.setDescription(null);

        assertNull(dto.getId());
        assertNull(dto.getCode());
        assertNull(dto.getDescription());
    }
}