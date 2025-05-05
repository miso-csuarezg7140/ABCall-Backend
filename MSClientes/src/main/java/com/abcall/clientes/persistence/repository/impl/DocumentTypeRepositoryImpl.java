package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.domain.dto.DocumentTypeDto;
import com.abcall.clientes.persistence.mappers.IDocumentTypeMapper;
import com.abcall.clientes.persistence.repository.IDocumentTypeRepository;
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
