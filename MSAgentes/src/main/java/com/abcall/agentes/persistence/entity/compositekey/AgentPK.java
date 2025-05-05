package com.abcall.agentes.persistence.entity.compositekey;

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
public class AgentPK {

    @Column(name = "tipo_documento", nullable = false)
    private Integer documentType;

    @Column(name = "numero_documento", nullable = false)
    private String documentNumber;
}
