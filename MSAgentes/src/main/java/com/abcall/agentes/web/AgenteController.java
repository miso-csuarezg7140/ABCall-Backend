package com.abcall.agentes.web;

import com.abcall.agentes.domain.service.AgenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService agenteService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /*@GetMapping("/getIncidentes")
    public ResponseEntity<?> getIncidentes() {
        ResponseServiceDto response = agenteService.getIncidentes();
        return new ResponseEntity<>(HttpStatus, )
    }*/
}
