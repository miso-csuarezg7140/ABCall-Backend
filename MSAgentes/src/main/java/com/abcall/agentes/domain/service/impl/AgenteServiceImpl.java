package com.abcall.agentes.domain.service.impl;

import com.abcall.agentes.persistence.repository.AgenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl {

    private final AgenteRepository agenteRepository;
}
