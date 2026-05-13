package com.RodSolution.ocularhub.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @Email(message = "Email format is invalid")
        @Size(max = 100, message = "Email must be at most 100 characters long")
        String email,

        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
