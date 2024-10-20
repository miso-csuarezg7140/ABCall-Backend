package com.abcall.ia.web;

import com.abcall.ia.domain.service.IAService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class IAController {

    private final IAService iaService;

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
