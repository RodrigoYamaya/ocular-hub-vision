package com.RodSolution.ocularhub.model.dto;

import java.time.LocalDate;

public record PacienteResponseDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String observacoes
) {
}
