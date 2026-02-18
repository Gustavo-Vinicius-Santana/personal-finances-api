package br.com.finance_project.personal_finance_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthForgotPasswordRequestDTO(
        @Email @NotBlank String email
) {
}
