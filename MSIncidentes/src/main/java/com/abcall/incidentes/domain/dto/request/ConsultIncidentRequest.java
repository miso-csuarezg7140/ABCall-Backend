package com.abcall.incidentes.domain.dto.request;

import com.abcall.incidentes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request para consultar incidentes")
public class ConsultIncidentRequest {

    @Schema(description = "Tipo de documento del usuario", example = "1")
    @Pattern(regexp = Constants.VALIDACION_TIPO_DOC_USUARIO, message = "El parámetro tipoDocUsuario no cumple las validaciones.")
    @JsonProperty("tipoDocUsuario")
    private String userDocumentType;

    @Schema(description = "Número de documento del usuario", example = "123456789")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_USUARIO, message = "El parámetro numeroDocUsuario no cumple las validaciones.")
    @JsonProperty("numeroDocUsuario")
    private String userDocumentNumber;

    @Schema(description = "Número de documento del cliente", example = "987654321")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO_CLIENTE, message = "El parámetro numeroDocCliente no cumple las validaciones.")
    @JsonProperty("numeroDocCliente")
    private String clientDocumentNumber;

    @Schema(description = "Estado del incidente", example = "EN PROCESO")
    @Pattern(regexp = Constants.VALIDACION_ESTADO, message = "El parámetro estado no cumple las validaciones.")
    @JsonProperty("estado")
    private String status = Constants.ESTADO_DEFAULT;

    @Schema(description = "Fecha de inicio del rango de consulta", example = "2023/01/01")
    @Pattern(regexp = Constants.VALIDACION_FECHA, message = "El parámetro fechaInicio no cumple las validaciones.")
    @JsonProperty("fechaInicio")
    private String startDate;

    @Schema(description = "Fecha de fin del rango de consulta", example = "2023/12/31")
    @Pattern(regexp = Constants.VALIDACION_FECHA, message = "El parámetro fechaFin no cumple las validaciones.")
    @JsonProperty("fechaFin")
    private String endDate;

    @Schema(description = "Número de página para la paginación", example = "1")
    @Pattern(regexp = Constants.VALIDACION_3_DIGITOS, message = "El parámetro pagina no cumple las validaciones.")
    @JsonProperty("pagina")
    private String page;

    @Schema(description = "Tamaño de la página para la paginación", example = "10")
    @Pattern(regexp = Constants.VALIDACION_3_DIGITOS, message = "El paránetro tamanioPagina no cumple las validaciones.")
    @JsonProperty("tamanioPagina")
    private String pageSize;
}
