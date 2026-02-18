package br.com.finance_project.personal_finance_api.controller;

import br.com.finance_project.personal_finance_api.dto.*;
import br.com.finance_project.personal_finance_api.model.User;
import br.com.finance_project.personal_finance_api.service.UserAuthService;
import br.com.finance_project.personal_finance_api.service.UserAuthServiceIml;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthServiceIml userAuth;

    @PostMapping("login")
    public ResponseEntity<UserAuthResponse> login(
            @Valid @RequestBody UserAuthLoginRequestDTO userLogin
    ){
        return ResponseEntity.ok(userAuth.loginUser(userLogin));
    }

    @PostMapping("register")
    public ResponseEntity<UserAuthResponse> register(
            @Valid @RequestBody UserAuthRegisterRequestDTO userRegister
    ) {
        return ResponseEntity.ok(userAuth.registerUser(userRegister));
    }

    @GetMapping("me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userAuth.getCurrentUser(user));
    }

    @PutMapping("update")
    public ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserAuthUpdateRequest request
    ) {
        return ResponseEntity.ok(userAuth.updateUser(user, request));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User user
    ) {
        userAuth.deleteUser(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @RequestBody @Valid UserAuthForgotPasswordRequestDTO request) {

        userAuth.requestPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody @Valid UserAuthResetPasswordRequestDTO request) {

        userAuth.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

}
