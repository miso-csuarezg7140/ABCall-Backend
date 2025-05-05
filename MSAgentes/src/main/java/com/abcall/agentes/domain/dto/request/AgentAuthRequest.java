package com.abcall.agentes.domain.dto.request;

import com.abcall.agentes.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request para autenticar un agente")
public class AgentAuthRequest {

    @Schema(description = "Tipo de documento del agente")
    @Pattern(regexp = Constants.VALIDACION_TIPO_DOCUMENTO, message = "El campo tipoDocumento no cumple las validaciones.")
    @NotBlank(message = "El campo tipoDocumento no cumple las validaciones.")
    @JsonProperty("tipoDocumento")
    private String documentType;

    @Schema(description = "Número de documento del agente")
    @Pattern(regexp = Constants.VALIDACION_DOCUMENTO, message = "El campo numeroDocumento no cumple las validaciones.")
    @NotBlank(message = "El campo numeroDocumento no cumple las validaciones.")
    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @Schema(description = "Contraseña del agente")
    @NotBlank(message = "El campo contrasena no cumple las validaciones.")
    @JsonProperty("contrasena")
    private String password;
}
