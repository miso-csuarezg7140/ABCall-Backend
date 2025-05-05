package com.abcall.agentes.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_documento")
public class DocumentType {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo")
    private String code;

    @Column(name = "descripcion")
    private String description;
}