package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.*;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceIml implements UserDetailsService, UserAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserAuthResponse registerUser(UserAuthRegisterRequestDTO userRegister) {
        User user = new User();
        user.setEmail(userRegister.email());
        user.setName(userRegister.name());
        user.setPassword(passwordEncoder.encode(userRegister.password()));

        User saveUser = userRepository.save(user);

        String token = jwtService.generateUserToken(user);

        return new UserAuthResponse(saveUser, token);
    }

    @Override
    public UserAuthResponse loginUser(UserAuthLoginRequestDTO userLogin) {
        User user = userRepository.findByEmailIgnoreCase(userLogin.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userLogin.email()));

        if(!passwordEncoder.matches(userLogin.password(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateUserToken(user);

        return new UserAuthResponse(user, token);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email) );
    }

    @Override
    public UserResponse getCurrentUser(User user) {
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(
            User user, UserAuthUpdateRequest userUpdate
    ) {

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(
                userUpdate.email(), user.getId())
        ) {

            throw new RuntimeException("Email already in use");
        }

        user.setName(userUpdate.name());
        user.setEmail(userUpdate.email());

        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    @Transactional
    public Void deleteUser(User user) {

        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(user.getId());

        return null;
    }
}
