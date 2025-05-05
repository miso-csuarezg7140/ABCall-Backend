package com.abcall.clientes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserClientDto {

    private Integer documentTypeUser;
    private Long documentUser;
    private Integer idClient;
}
