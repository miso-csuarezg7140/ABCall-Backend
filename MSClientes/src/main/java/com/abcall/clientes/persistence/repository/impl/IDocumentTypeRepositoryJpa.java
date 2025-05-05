package com.abcall.clientes.persistence.repository.impl;

import com.abcall.clientes.persistence.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocumentTypeRepositoryJpa extends JpaRepository<DocumentType, Integer> {
}
