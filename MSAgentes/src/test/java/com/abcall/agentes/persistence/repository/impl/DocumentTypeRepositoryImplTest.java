package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.persistence.entity.DocumentType;
import com.abcall.agentes.persistence.mappers.IDocumentTypeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentTypeRepositoryImplTest {

    @Mock
    private IDocumentTypeRepositoryJpa documentTypeRepositoryJpa;

    @Mock
    private IDocumentTypeMapper documentTypeMapper;

    @InjectMocks
    private DocumentTypeRepositoryImpl documentTypeRepository;

    @Test
    void getListWithEmptyDataReturnsEmptyList() {
        when(documentTypeRepositoryJpa.findAll()).thenReturn(List.of());
        when(documentTypeMapper.toDtoList(List.of())).thenReturn(List.of());

        List<DocumentTypeDto> result = documentTypeRepository.getList();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getListWithValidDataReturnsMappedDtoList() {
        List<DocumentType> documentTypes = List.of(
                new DocumentType(),
                new DocumentType()
        );
        documentTypes.getFirst().setId(1);
        documentTypes.getFirst().setCode("DNI");
        documentTypes.getFirst().setDescription("National ID");

        documentTypes.get(1).setId(2);
        documentTypes.get(1).setCode("PASSPORT");
        documentTypes.get(1).setDescription("Passport");

        List<DocumentTypeDto> expectedDtos = List.of(
                new DocumentTypeDto(),
                new DocumentTypeDto()
        );
        expectedDtos.getFirst().setId(1);
        expectedDtos.getFirst().setCode("DNI");
        expectedDtos.getFirst().setDescription("National ID");

        expectedDtos.get(1).setId(2);
        expectedDtos.get(1).setCode("PASSPORT");
        expectedDtos.get(1).setDescription("Passport");

        when(documentTypeRepositoryJpa.findAll()).thenReturn(documentTypes);
        when(documentTypeMapper.toDtoList(documentTypes)).thenReturn(expectedDtos);

        List<DocumentTypeDto> result = documentTypeRepository.getList();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("DNI", result.get(0).getCode());
        assertEquals("National ID", result.get(0).getDescription());
        assertEquals("PASSPORT", result.get(1).getCode());
        assertEquals("Passport", result.get(1).getDescription());
    }
}