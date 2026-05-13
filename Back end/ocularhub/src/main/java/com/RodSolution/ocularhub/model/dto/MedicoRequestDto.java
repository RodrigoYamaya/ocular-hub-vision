package com.RodSolution.ocularhub.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicoRequestDto(
        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @NotBlank(message = "O CRM é obrigatório")
        @Size(min = 4, max = 20, message = "O CRM deve ter entre 4 e 20 caracteres")
        String crm,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Insira um e-mail válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        String especialidade

) {
}
