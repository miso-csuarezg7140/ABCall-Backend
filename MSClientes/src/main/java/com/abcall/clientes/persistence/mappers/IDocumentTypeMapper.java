package com.abcall.clientes.persistence.mappers;

import com.abcall.clientes.domain.dto.DocumentTypeDto;
import com.abcall.clientes.persistence.entity.DocumentType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDocumentTypeMapper {

    List<DocumentTypeDto> toDtoList(List<DocumentType> documentTypeList);
}
