package com.abcall.incidentes.web;

import com.abcall.incidentes.domain.service.IncidenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class IncidenteController {

    private final IncidenteService incidentesService;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
