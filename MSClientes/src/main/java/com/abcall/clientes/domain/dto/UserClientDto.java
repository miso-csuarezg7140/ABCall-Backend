package com.abcall.clientes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserClientDto {

    private String documentTypeUser;
    private Long documentNumberUser;
    private Integer idClient;
}
