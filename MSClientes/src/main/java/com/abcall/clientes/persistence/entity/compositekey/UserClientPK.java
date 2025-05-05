package com.abcall.clientes.persistence.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserClientPK {

    @Column(name = "tipo_doc_usuario", nullable = false)
    private Integer documentTypeUser;

    @Column(name = "numero_doc_usuario", nullable = false)
    private Long documentUser;

    @Column(name = "id_cliente", nullable = false)
    private Integer idClient;
}
