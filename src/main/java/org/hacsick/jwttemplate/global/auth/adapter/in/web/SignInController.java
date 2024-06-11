package org.hacsick.jwttemplate.global.auth.adapter.in.web;

import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignInCommand;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignInUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInController {

    private final SignInUseCase signinUseCase;

    public SignInController(final SignInUseCase signinUseCase) {
        this.signinUseCase = signinUseCase;
    }

    @PostMapping("/api/v1/auth/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInCommand signInRequest) {
        return ResponseEntity.ok(this.signinUseCase.signIn(signInRequest));
    }
}
