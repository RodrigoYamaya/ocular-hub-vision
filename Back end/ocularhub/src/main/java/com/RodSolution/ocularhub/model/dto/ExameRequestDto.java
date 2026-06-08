package com.RodSolution.ocularhub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExameRequestDto(
        @NotNull(message = "O ID do paciente é obrigatório")
        Long pacienteId,

        @NotBlank(message = "O título do exame é obrigatório")
        String titulo,

        String regiaoAnalisada,
        Double precisaoIa,
        String diagnosticoIa
) {
}
