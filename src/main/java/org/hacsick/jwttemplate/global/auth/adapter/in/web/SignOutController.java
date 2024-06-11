package org.hacsick.jwttemplate.global.auth.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignOutUseCase;
import org.hacsick.jwttemplate.global.common.LoginUser;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignOutController {

    private final SignOutUseCase signOutUseCase;

    public SignOutController(final SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    @PostMapping("/api/v1/auth/signout")
    public ResponseEntity<Void> signOut(final HttpServletRequest httpServletRequest, @LoginUser User user) {
        this.signOutUseCase.signOut(httpServletRequest, user);
        return ResponseEntity.noContent().build();
    }
}
