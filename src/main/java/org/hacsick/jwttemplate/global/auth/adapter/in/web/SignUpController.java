package org.hacsick.jwttemplate.global.auth.adapter.in.web;

import jakarta.validation.Valid;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignUpRequest;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.validator.SignUpRequestPassword;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.validator.SignUpRequestPattern;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignUpUseCase;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private final SignUpUseCase signUpUseCase;
    private final SignUpRequestPassword signUpRequestPassword;
    private final SignUpRequestPattern signUpRequestPattern;

    public SignUpController(final SignUpUseCase signUpUseCase,
                            final SignUpRequestPassword signUpRequestPassword,
                            final SignUpRequestPattern signUpRequestPattern) {
        this.signUpUseCase = signUpUseCase;
        this.signUpRequestPassword = signUpRequestPassword;
        this.signUpRequestPattern = signUpRequestPattern;
    }

    @InitBinder("signUpRequest")
    public void signUpInitBinder(final WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpRequestPassword, signUpRequestPattern);
    }


    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        this.signUpUseCase.signUp(signUpRequest.toCommand());
        return ResponseEntity.ok().build();
    }
}
