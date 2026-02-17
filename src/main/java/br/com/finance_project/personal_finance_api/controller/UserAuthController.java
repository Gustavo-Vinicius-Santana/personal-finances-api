package br.com.finance_project.personal_finance_api.controller;

import br.com.finance_project.personal_finance_api.dto.UserAuthLoginRequestDTO;
import br.com.finance_project.personal_finance_api.dto.UserAuthRegisterRequestDTO;
import br.com.finance_project.personal_finance_api.dto.UserAuthResponse;
import br.com.finance_project.personal_finance_api.service.UserAuthService;
import br.com.finance_project.personal_finance_api.service.UserAuthServiceIml;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("userByToken")
    public ResponseEntity<UserAuthResponse> userByToken() {
        return null;
    }

}
