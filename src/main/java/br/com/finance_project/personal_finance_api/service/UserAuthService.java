package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.UserAuthLoginRequestDTO;
import br.com.finance_project.personal_finance_api.dto.UserAuthRegisterRequestDTO;
import br.com.finance_project.personal_finance_api.dto.UserAuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthService {
    UserAuthResponse registerUser(UserAuthRegisterRequestDTO userRegister);
    UserAuthResponse loginUser(UserAuthLoginRequestDTO userLogin);
    UserDetails loadUserByUsername(String username);
}
