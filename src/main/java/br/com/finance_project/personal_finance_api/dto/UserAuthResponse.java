package br.com.finance_project.personal_finance_api.dto;

import br.com.finance_project.personal_finance_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthResponse(
        @NotBlank String name,
        String token,
        String refresh
) {
    public UserAuthResponse(User user, String token, String refresh){
        this(
                user.getName(),
                token,
                refresh
        );
    }
}
