package com.abcall.reporteria.persistence.mappers;

import com.abcall.reporteria.domain.dto.ReporteriaDto;
import com.abcall.reporteria.persistence.entity.Reporteria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReporteriaMapper {

    Reporteria toReporteria(ReporteriaDto reporteriaDto);

    @InheritInverseConfiguration
    ReporteriaDto fromReporteria(Reporteria reporteria);
}
