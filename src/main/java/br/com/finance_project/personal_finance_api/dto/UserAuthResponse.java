package br.com.finance_project.personal_finance_api.dto;

import br.com.finance_project.personal_finance_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthResponse(
        Long id,
        @NotBlank String name,
        @NotBlank @Email String email,
        String token
) {
    public UserAuthResponse(User user, String token){
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token
        );
    }
}
