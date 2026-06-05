package com.RodSolution.ocularhub.model.dto;

import java.time.LocalDate;

public record PacienteResponseDto(
        Long id,
        String nome,
        Integer idade,
        String cpf,
        LocalDate dataNascimento,
        String observacao
) {
}
