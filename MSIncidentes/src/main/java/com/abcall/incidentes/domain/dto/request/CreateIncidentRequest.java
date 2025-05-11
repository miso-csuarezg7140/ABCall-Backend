package com.abcall.incidentes.domain.dto.request;

import com.abcall.incidentes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Clase que representa la solicitud para crear un incidente.")
public class CreateIncidentRequest {

    @Schema(description = "Tipo de documento del usuario que reporta el incidente.", examples = "1")
    @Pattern(regexp = Constants.VALIDACION_TIPO_DOC_USUARIO, message = "El campo tipoDocumentoUsuario no cumple las validaciones.")
    @NotBlank(message = "El campo tipoDocumentoUsuario no puede ser nulo.")
    @JsonProperty("tipoDocumentoUsuario")
    private String userDocumentType;

    @Schema(description = "Número de documento del usuario que reporta el incidente.", examples = "123456789")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_USUARIO, message = "El campo numDocumentoUsuario no cumple las validaciones.")
    @NotBlank(message = "El campo numDocumentoUsuario no puede ser nulo.")
    @JsonProperty("numDocumentoUsuario")
    private String userDocumentNumber;

    @Schema(description = "Tipo de documento del cliente asociado al incidente.", examples = "98765432")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El campo numDocumentoCliente no cumple las validaciones.")
    @NotBlank(message = "El campo numDocumentoCliente no puede ser nulo.")
    @JsonProperty("numDocumentoCliente")
    private String userDocumentClient;

    @Schema(description = "Descripción del incidente.", examples = "Incidente de prueba")
    @NotBlank(message = "El campo descripcion no puede ser nulo.")
    @JsonProperty("descripcion")
    private String description;

    private Boolean solved = false;
    private String status = Constants.ESTADO_DEFAULT;
    private String createdBy = Constants.USUARIO_DEFAULT;
    private LocalDateTime createdDate = LocalDateTime.now();
}
