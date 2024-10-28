package com.abcall.agentes.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgenteDto {

    private String tipoDocumento;
    private Long numeroDocumento;
    private String contrasena;
    private String nombres;
    private String apellidos;
}
