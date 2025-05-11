package com.abcall.incidentes.domain.dto.request;

import com.abcall.incidentes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Clase que representa la solicitud de actualización de un incidente.")
public class UpdateIncidentRequest {

    @Schema(description = "ID del incidente", example = "123456")
    @Pattern(regexp = Constants.VALIDACION_NUMERICO, message = "El campo idIncidente no cumple las validaciones.")
    @NotBlank(message = "El campo idIncidente no cumple las validaciones.")
    @JsonProperty("idIncidente")
    private String incidentId;

    @Schema(description = "Tipo de documento del usuario", example = "1")
    @Pattern(regexp = Constants.VALIDACION_TIPO_DOC_USUARIO, message = "El campo tipoDocumentoUsuario no cumple las validaciones.")
    @NotBlank(message = "El campo tipoDocumentoUsuario no cumple las validaciones.")
    @JsonProperty("tipoDocumentoUsuario")
    private String userDocumentType;

    @Schema(description = "Número de documento del usuario", example = "123456789")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_USUARIO, message = "El campo numDocumentoUsuario no cumple las validaciones.")
    @NotBlank(message = "El campo numDocumentoUsuario no cumple las validaciones.")
    @JsonProperty("numDocumentoUsuario")
    private String userDocumentNumber;

    @Schema(description = "Número de documento del cliente", example = "987654321")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El campo numDocumentoCliente no cumple las validaciones.")
    @NotBlank(message = "El campo numDocumentoCliente no cumple las validaciones.")
    @JsonProperty("numDocumentoCliente")
    private String clientDocumentNumber;

    @Schema(description = "Valor booleano que indica la solución del incidente", example = "true")
    @NotNull(message = "El campo solucionado no cumple las validaciones.")
    @JsonProperty("solucionado")
    private Boolean solved;

    @Schema(description = "Estado del incidente", example = "SOLUCIONADO")
    @NotBlank(message = "El campo estado no cumple las validaciones.")
    @JsonProperty("estado")
    private String status;

    @Schema(description = "Agente que modifica el incidente", example = "1")
    @NotBlank(message = "El campo modificadoPor no cumple las validaciones.")
    @JsonProperty("modificadoPor")
    private String modifiedBy;

    private LocalDateTime modifiedDate = LocalDateTime.now();
}