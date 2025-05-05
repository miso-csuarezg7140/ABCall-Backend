package com.abcall.agentes.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class DocumentTypeTest {

    @Test
    void emptyConstructorCreatesObjectWithNullFields() {
        DocumentType documentType = new DocumentType();

        assertNull(documentType.getId());
        assertNull(documentType.getCode());
        assertNull(documentType.getDescription());
    }

    @Test
    void settersAndGettersWorkCorrectly() {
        DocumentType documentType = new DocumentType();
        documentType.setId(1);
        documentType.setCode("DNI");
        documentType.setDescription("National ID");

        assertEquals(1, documentType.getId());
        assertEquals("DNI", documentType.getCode());
        assertEquals("National ID", documentType.getDescription());
    }

    @Test
    void fieldsCanBeUpdated() {
        DocumentType documentType = new DocumentType();
        documentType.setId(1);
        documentType.setCode("DNI");
        documentType.setDescription("National ID");

        documentType.setId(2);
        documentType.setCode("PASSPORT");
        documentType.setDescription("Passport");

        assertEquals(2, documentType.getId());
        assertEquals("PASSPORT", documentType.getCode());
        assertEquals("Passport", documentType.getDescription());
    }

    @Test
    void fieldsCanBeSetToNull() {
        DocumentType documentType = new DocumentType();
        documentType.setId(1);
        documentType.setCode("DNI");
        documentType.setDescription("National ID");

        documentType.setId(null);
        documentType.setCode(null);
        documentType.setDescription(null);

        assertNull(documentType.getId());
        assertNull(documentType.getCode());
        assertNull(documentType.getDescription());
    }
}