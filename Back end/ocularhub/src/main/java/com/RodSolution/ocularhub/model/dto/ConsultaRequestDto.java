package com.RodSolution.ocularhub.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequestDto(
        @NotNull(message = "O ID do médico é obrigatório")
        Long medicoId,

        @NotNull(message = "O ID do paciente é obrigatório")
        Long pacienteId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A data da consulta deve estar no futuro")
        LocalDateTime dataHora
) {
}
