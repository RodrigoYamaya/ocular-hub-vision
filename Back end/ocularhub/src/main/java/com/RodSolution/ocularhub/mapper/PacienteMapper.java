package com.RodSolution.ocularhub.mapper;

import com.RodSolution.ocularhub.model.Paciente;
import com.RodSolution.ocularhub.model.dto.PacienteRequestDto;
import com.RodSolution.ocularhub.model.dto.PacienteResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    @Mapping(target = "id", ignore = true)
    Paciente toEntity(PacienteRequestDto dto);

    // Transforma o que vem do Banco de Dados (Entity) em Resposta para o Front (DTO)
    PacienteResponseDto toDto(Paciente entity);

}
