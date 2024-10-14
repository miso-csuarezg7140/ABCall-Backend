package com.abcall.agentes.persistence.repository.impl;

import com.abcall.agentes.persistence.repository.AgenteRepository;
import com.abcall.agentes.persistence.repository.jpa.AgenteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AgenteRepositoryImpl implements AgenteRepository {

    private final AgenteRepositoryJpa agenteRepositoryJpa;
}
