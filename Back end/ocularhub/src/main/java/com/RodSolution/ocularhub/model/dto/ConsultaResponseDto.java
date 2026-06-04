package com.RodSolution.ocularhub.model.dto;

import java.time.LocalDateTime;

public record ConsultaResponseDto(
        Long id,
        Long medicoId,
        Long pacienteId,
        LocalDateTime dataHora,
        String motivoCancelamento

) {
}
