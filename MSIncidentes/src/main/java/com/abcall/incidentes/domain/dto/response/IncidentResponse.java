package com.abcall.incidentes.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("tipoDocumentoUsuario")
    private Integer userDocumentType;
    @JsonProperty("numDocumentoUsuario")
    private String userDocumentNumber;
    @JsonProperty("numDocumentoCliente")
    private Long clientDocumentNumber;
    @JsonProperty("descripcion")
    private String description;
}
