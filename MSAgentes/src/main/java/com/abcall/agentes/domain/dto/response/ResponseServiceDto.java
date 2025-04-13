package com.abcall.agentes.domain.dto.response;

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
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "statusCode",
        "statusDescription",
        "data"
})
public class ResponseServiceDto {

    @Schema(description = "Código de estado", example = "200")
    @JsonProperty("statusCode")
    private Integer statusCode;

    @Schema(description = "Descripción de estado", example = "Consulta exitosa.")
    @JsonProperty("statusDescription")
    private String statusDescription;

    @Schema(description = "Data", example = "")
    @JsonProperty("data")
    private Object data;
}