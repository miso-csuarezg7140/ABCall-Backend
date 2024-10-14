package com.abcall.incidentes.persistence.repository.impl;

import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import com.abcall.incidentes.persistence.repository.jpa.IncidenteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IncidenteRepositoryImpl implements IncidenteRepository {

    private final IncidenteRepositoryJpa incidenteRepositoryJpa;
}
