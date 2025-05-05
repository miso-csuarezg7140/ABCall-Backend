package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.persistence.mappers.IDocumentTypeMapper;
import com.abcall.agentes.persistence.repository.IDocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DocumentTypeRepositoryImpl implements IDocumentTypeRepository {

    private final IDocumentTypeRepositoryJpa documentTypeRepositoryJpa;
    private final IDocumentTypeMapper documentTypeMapper;

    @Override
    public List<DocumentTypeDto> getList() {
        return documentTypeMapper.toDtoList(documentTypeRepositoryJpa.findAll());
    }
}
