package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.persistence.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocumentTypeRepositoryJpa extends JpaRepository<DocumentType, Integer> {
}
