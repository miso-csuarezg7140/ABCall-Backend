package com.abcall.incidentes.domain.service.impl;

import com.abcall.incidentes.persistence.repository.IncidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidenteServiceImpl {

    private final IncidenteRepository incidenteRepository;
}
