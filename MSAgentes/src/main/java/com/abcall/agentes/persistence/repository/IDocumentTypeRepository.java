package com.abcall.agentes.persistence.repository;

import com.abcall.agentes.domain.dto.DocumentTypeDto;

import java.util.List;

public interface IDocumentTypeRepository {

    List<DocumentTypeDto> getList();
}
