package com.RodSolution.ocularhub.model.dto;

import java.time.LocalDateTime;

public record ExameResponseDto(
        Long id,
        String titulo,
        String regiaoAnalisada,
        Double precisaoIa,
        String diagnosticoIa,
        String status, 
        LocalDateTime dataRegistro,
        Long pacienteId
) {
}
