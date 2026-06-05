package com.RodSolution.ocularhub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PacienteRequestDto(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message= "idade e obrigatorio")
        Integer idade,

        @NotBlank(message = "O CPF é obrigatório")
        //@CPF(message = "Formato de CPF inválido")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres")
        String observacao
) {
}
