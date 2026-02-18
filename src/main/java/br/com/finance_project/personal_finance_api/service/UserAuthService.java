package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.*;
import br.com.finance_project.personal_finance_api.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthService {
    UserAuthResponse registerUser(UserAuthRegisterRequestDTO userRegister);
    UserAuthResponse loginUser(UserAuthLoginRequestDTO userLogin);
    UserDetails loadUserByUsername(String username);
    UserResponse getCurrentUser(User user);
    UserResponse updateUser(User user, UserAuthUpdateRequest userUpdate);
    Void deleteUser(User user);
}
