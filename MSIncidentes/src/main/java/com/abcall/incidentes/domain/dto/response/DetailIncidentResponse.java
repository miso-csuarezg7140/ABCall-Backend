package com.abcall.incidentes.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetailIncidentResponse {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("tipoDocumentoUsuario")
    private String userDocumentType;
    @JsonProperty("numDocumentoUsuario")
    private Long userDocumentNumber;
    @JsonProperty("numDocumentoCliente")
    private Long userDocumentClient;
    @JsonProperty("descripcion")
    private String description;
    @JsonProperty("solucionado")
    private Boolean solved;
    @JsonProperty("solucionId")
    private Integer idSolution;
    @JsonProperty("solucionadoPor")
    private String solvedBy;
    @JsonProperty("fechaSolucion")
    private LocalDateTime solutionDate;
    @JsonProperty("estado")
    private String status;
    @JsonProperty("creadoPor")
    private String createdBy;
    @JsonProperty("fechaCreacion")
    private LocalDateTime createdDate;
    @JsonProperty("modificadoPor")
    private String modifiedBy;
    @JsonProperty("fechaModificacion")
    private LocalDateTime modifiedDate;
}
