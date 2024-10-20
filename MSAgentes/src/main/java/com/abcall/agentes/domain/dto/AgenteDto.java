package com.abcall.agentes.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgenteDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private Character tipoDocumento;
    private Long documento;
}
