package br.com.finance_project.personal_finance_api.dto;

public record UserAuthChangeEmailRequestDTO(
        String currentEmail,
        String newEmail,
        String password
) {

}
