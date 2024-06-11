package org.hacsick.jwttemplate.global.auth.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.application.port.in.RefreshTokenUseCase;
import org.hacsick.jwttemplate.global.common.LoginUser;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    private final RefreshTokenUseCase refreshTokenUseCase;

    public RefreshTokenController(final RefreshTokenUseCase refreshTokenUseCase) {
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping("/api/v1/auth/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(final HttpServletRequest httpServletRequest,
                                                               @LoginUser User user) {
        return ResponseEntity.ok(this.refreshTokenUseCase.refreshToken(httpServletRequest, user));
    }

}
