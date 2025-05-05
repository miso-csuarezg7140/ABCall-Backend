package com.abcall.agentes.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AgentDto {

    private Integer documentType;
    private String documentNumber;
    private String password;
    private String names;
    private String surnames;
    private LocalDateTime lastLogin;
}
