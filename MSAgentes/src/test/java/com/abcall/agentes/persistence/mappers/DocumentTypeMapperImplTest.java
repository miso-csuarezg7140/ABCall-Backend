package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.persistence.entity.DocumentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DocumentTypeMapperImplTest {

    @Test
    void toDtoListReturnsNullWhenInputListIsNull() {
        IDocumentTypeMapperImpl mapper = new IDocumentTypeMapperImpl();

        List<DocumentTypeDto> result = mapper.toDtoList(null);

        assertNull(result);
    }

    @Test
    void toDtoListReturnsEmptyListWhenInputListIsEmpty() {
        IDocumentTypeMapperImpl mapper = new IDocumentTypeMapperImpl();

        List<DocumentTypeDto> result = mapper.toDtoList(new ArrayList<>());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toDtoListMapsAllElementsCorrectly() {
        DocumentType documentType1 = new DocumentType();
        documentType1.setId(1);
        documentType1.setCode("CODE1");
        documentType1.setDescription("Description1");

        DocumentType documentType2 = new DocumentType();
        documentType2.setId(2);
        documentType2.setCode("CODE2");
        documentType2.setDescription("Description2");

        List<DocumentType> documentTypeList = List.of(documentType1, documentType2);

        IDocumentTypeMapperImpl mapper = new IDocumentTypeMapperImpl();

        List<DocumentTypeDto> result = mapper.toDtoList(documentTypeList);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(documentType1.getId(), result.getFirst().getId());
        assertEquals(documentType1.getCode(), result.getFirst().getCode());
        assertEquals(documentType1.getDescription(), result.getFirst().getDescription());
        assertEquals(documentType2.getId(), result.get(1).getId());
        assertEquals(documentType2.getCode(), result.get(1).getCode());
        assertEquals(documentType2.getDescription(), result.get(1).getDescription());
    }

    @Test
    void documentTypeToDocumentTypeDtoReturnsNullWhenInputIsNull() {
        IDocumentTypeMapperImpl mapper = new IDocumentTypeMapperImpl();

        DocumentTypeDto result = mapper.documentTypeToDocumentTypeDto(null);

        assertNull(result);
    }

    @Test
    void documentTypeToDocumentTypeDtoMapsFieldsCorrectly() {
        DocumentType documentType = new DocumentType();
        documentType.setId(1);
        documentType.setCode("CODE");
        documentType.setDescription("Description");

        IDocumentTypeMapperImpl mapper = new IDocumentTypeMapperImpl();

        DocumentTypeDto result = mapper.documentTypeToDocumentTypeDto(documentType);

        assertNotNull(result);
        assertEquals(documentType.getId(), result.getId());
        assertEquals(documentType.getCode(), result.getCode());
        assertEquals(documentType.getDescription(), result.getDescription());
    }
}