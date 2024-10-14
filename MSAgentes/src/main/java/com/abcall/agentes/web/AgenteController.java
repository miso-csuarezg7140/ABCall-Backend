package com.abcall.agentes.web;

import com.abcall.agentes.domain.service.AgenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService agenteService;
}
