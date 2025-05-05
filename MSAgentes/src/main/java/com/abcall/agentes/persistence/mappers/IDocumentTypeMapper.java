package com.abcall.agentes.persistence.mappers;

import com.abcall.agentes.domain.dto.DocumentTypeDto;
import com.abcall.agentes.persistence.entity.DocumentType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDocumentTypeMapper {

    List<DocumentTypeDto> toDtoList(List<DocumentType> documentTypeList);
}
