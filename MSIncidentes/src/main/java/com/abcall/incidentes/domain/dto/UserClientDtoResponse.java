package com.abcall.incidentes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserClientDtoResponse {

    private String documentTypeUser;
    private Long documentNumberUser;
    private Integer idClient;
}
