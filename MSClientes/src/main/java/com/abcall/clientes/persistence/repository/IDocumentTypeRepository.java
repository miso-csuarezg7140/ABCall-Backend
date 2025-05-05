package com.abcall.clientes.persistence.repository;

import com.abcall.clientes.domain.dto.DocumentTypeDto;

import java.util.List;

public interface IDocumentTypeRepository {

    List<DocumentTypeDto> getList();
}
