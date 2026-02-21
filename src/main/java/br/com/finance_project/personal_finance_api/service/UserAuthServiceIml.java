package br.com.finance_project.personal_finance_api.service;

import br.com.finance_project.personal_finance_api.dto.*;
import br.com.finance_project.personal_finance_api.exception.BusinessException;
import br.com.finance_project.personal_finance_api.exception.ResourceNotFoundException;
import br.com.finance_project.personal_finance_api.model.PasswordResetCode;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.repository.PasswordResetCodeRepository;
import br.com.finance_project.personal_finance_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthServiceIml implements UserDetailsService, UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetCodeRepository resetRepository;
    private final EmailService emailService;

    @Override
    public UserAuthResponse registerUser(UserAuthRegisterRequestDTO userRegister) {

        if (userRepository.existsByEmailIgnoreCase(userRegister.email())) {
            throw new BusinessException("Email already in use");
        }

        User user = new User();
        user.setEmail(userRegister.email());
        user.setName(userRegister.name());
        user.setPassword(passwordEncoder.encode(userRegister.password()));

        User saveUser = userRepository.save(user);

        String token = jwtService.generateUserToken(saveUser);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new UserAuthResponse(saveUser, token, refreshToken);
    }

    @Override
    public UserAuthResponse loginUser(UserAuthLoginRequestDTO userLogin) {

        User user = userRepository.findByEmailIgnoreCase(userLogin.email())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(userLogin.password(), user.getPassword())) {
            throw new BusinessException(
                    "Invalid credentials",
                    HttpStatus.UNAUTHORIZED
            );
        }

        String accessToken = jwtService.generateUserToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new UserAuthResponse(user, accessToken, refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserResponse getCurrentUser(User user) {
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(
            User user,
            UserAuthUpdateRequest userUpdate
    ) {

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(
                userUpdate.email(), user.getId())
        ) {
            throw new BusinessException("Email already in use");
        }

        user.setName(userUpdate.name());
        user.setEmail(userUpdate.email());

        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    @Transactional
    public Void deleteUser(User user) {

        if (!userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(user.getId());

        return null;
    }

    @Transactional
    public void requestPasswordReset(UserAuthForgotPasswordRequestDTO request) {

        userRepository.findByEmailIgnoreCase(request.email())
                .ifPresent(user -> {

                    resetRepository.deleteByEmail(user.getEmail());

                    String code = generateSecureCode();

                    PasswordResetCode resetCode = new PasswordResetCode();
                    resetCode.setEmail(user.getEmail());
                    resetCode.setCode(code);
                    resetCode.setExpiration(
                            LocalDateTime.now().plusMinutes(15));
                    resetCode.setUsed(false);

                    resetRepository.save(resetCode);

                    emailService.sendResetCode(user.getEmail(), code);
                });
    }

    @Transactional
    public void resetPassword(UserAuthResetPasswordRequestDTO request) {

        PasswordResetCode resetCode = resetRepository
                .findByEmailAndCodeAndUsedFalse(
                        request.email(), request.code())
                .orElseThrow(() ->
                        new BusinessException("Invalid reset code"));

        if (resetCode.getExpiration()
                .isBefore(LocalDateTime.now())) {

            throw new BusinessException("Reset code expired");
        }

        User user = userRepository
                .findByEmailIgnoreCase(request.email())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setPassword(
                passwordEncoder.encode(request.newPassword()));

        resetCode.setUsed(true);

        userRepository.save(user);
        resetRepository.save(resetCode);
    }

    @Override
    public UserAuthResponse refreshToken(String refreshToken) {

        String tokenType = jwtService.extractTokenType(refreshToken);

        if (tokenType == null || !tokenType.equals("refresh")) {
            throw new BusinessException("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }

        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new BusinessException("Refresh token expired or invalid", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new BusinessException("Refresh token does not match", HttpStatus.UNAUTHORIZED);
        }

        String newAccessToken = jwtService.generateUserToken(user);

        return new UserAuthResponse(user, newAccessToken, refreshToken);
    }

    private String generateSecureCode() {
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
