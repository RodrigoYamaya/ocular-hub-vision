package com.RodSolution.ocularhub.model.dto;

public record MedicoResponseDto(
        Long id,
        String nome,
        String crm,
        String email,
        String especialidade
) {
}
