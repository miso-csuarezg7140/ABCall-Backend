package com.abcall.ia.persistence.mappers;

import com.abcall.ia.domain.dto.IADto;
import com.abcall.ia.persistence.entity.IA;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAMapper {

    IA toIA(IADto iaDto);

    @InheritInverseConfiguration
    IADto fromIA(IA ia);
}
