package com.abcall.clientes.persistence.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class UserClientPK {

    @Column(name = "tipo_doc_usuario", nullable = false)
    private String tipoDocUsuario;

    @Column(name = "numero_doc_usuario", nullable = false)
    private Long numeroDocUsuario;

    @Column(name = "numero_doc_cliente", nullable = false)
    private Long numeroDocCliente;
}
