package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.domain.service.AgenteService;
import com.abcall.agentes.persistence.repository.AgenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl implements AgenteService {

    private final AgenteRepository agenteRepository;
}
