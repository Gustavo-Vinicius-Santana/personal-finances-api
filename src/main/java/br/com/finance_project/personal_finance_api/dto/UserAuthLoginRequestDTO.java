package br.com.finance_project.personal_finance_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthLoginRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
