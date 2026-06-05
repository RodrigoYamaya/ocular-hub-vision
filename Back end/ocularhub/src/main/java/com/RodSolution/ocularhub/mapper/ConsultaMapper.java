package com.RodSolution.ocularhub.mapper;

import com.RodSolution.ocularhub.model.entities.Consulta;
import com.RodSolution.ocularhub.model.dto.ConsultaRequestDto;
import com.RodSolution.ocularhub.model.dto.ConsultaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "motivoCancelamento", ignore = true)
    Consulta toEntity(ConsultaRequestDto dto);

    @Mapping(source = "medico.id", target = "medicoId")
    @Mapping(source = "paciente.id", target = "pacienteId")
    ConsultaResponseDto toDto(Consulta entity);


}
