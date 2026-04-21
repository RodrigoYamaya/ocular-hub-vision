package com.RodSolution.ocularhub.mapper;

import com.RodSolution.ocularhub.model.Medico;
import com.RodSolution.ocularhub.model.dto.MedicoRequestDto;
import com.RodSolution.ocularhub.model.dto.MedicoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    @Mapping(target = "id", ignore = true)
// Transforma o que vem do Front (DTO) em Banco de Dados (Entity)
    Medico toEntity(MedicoRequestDto dto);

    // Transforma o que vem do Banco de Dados (Entity) em Resposta para o Front (DTO)
    MedicoResponseDto toDto(Medico entity);
}
