package br.com.finance_project.personal_finance_api.dto;

import br.com.finance_project.personal_finance_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserResponse(
        Long id,
        @NotBlank String name,
        @NotBlank @Email String email
) {
    public UserResponse(User user){
        this(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}
