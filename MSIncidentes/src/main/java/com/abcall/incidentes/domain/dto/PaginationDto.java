package com.abcall.incidentes.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationDto {

    @JsonProperty("pagina")
    private Integer page;
    @JsonProperty("tamanioPagina")
    private Integer pageSize;
    @JsonProperty("totalPaginas")
    private Integer totalPages;
    @JsonProperty("totalElementos")
    private Long totalElements;
}
