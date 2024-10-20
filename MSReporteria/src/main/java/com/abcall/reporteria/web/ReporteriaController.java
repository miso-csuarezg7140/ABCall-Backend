package com.abcall.reporteria.web;

import com.abcall.reporteria.domain.service.ReporteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReporteriaController {

    private final ReporteriaService reporteriaService;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
