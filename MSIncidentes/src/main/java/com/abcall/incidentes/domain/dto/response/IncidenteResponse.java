package com.abcall.incidentes.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidenteResponse {

    private Integer id;
    private String tipoDocumentoUsuario;
    private Long numDocumentoUsuario;
    private Long numDocumentoCliente;
    private String descripcion;
}
