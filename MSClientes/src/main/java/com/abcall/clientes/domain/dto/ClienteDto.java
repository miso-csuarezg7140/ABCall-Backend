package com.abcall.clientes.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

    private Long id;
    private String razonSocial;
    private Long documento;
}
