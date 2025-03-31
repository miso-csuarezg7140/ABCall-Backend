package com.abcall.clientes.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "statusCode",
        "statusDescription"
})
public class ResponseServiceDto {

    @Schema(description = "Código de estado", example = "200")
    @JsonProperty("statusCode")
    private String statusCode;

    @Schema(description = "Descripción de estado", example = "Transacción exitosa.")
    @JsonProperty("statusDescription")
    private String statusDescription;

    @Schema(description = "Data", example = "[Registrado]")
    @JsonProperty("data")
    private Object data;
}