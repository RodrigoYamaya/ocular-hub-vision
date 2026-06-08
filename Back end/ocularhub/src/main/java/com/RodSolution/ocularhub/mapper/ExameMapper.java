package com.RodSolution.ocularhub.mapper;

import com.RodSolution.ocularhub.model.dto.ExameRequestDto;
import com.RodSolution.ocularhub.model.dto.ExameResponseDto;
import com.RodSolution.ocularhub.model.entities.Exame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExameMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataRegistro", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    Exame toEntity(ExameRequestDto dto);

    // 2. Converter Entity para DT
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(target = "status", expression = "java(exame.getStatus().getDescricao())")
    ExameResponseDto toDto(Exame exame);
}