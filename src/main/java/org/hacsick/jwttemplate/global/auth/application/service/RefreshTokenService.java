package org.hacsick.jwttemplate.global.auth.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.adapter.out.authentication.TokenProviderPortAdapter;
import org.hacsick.jwttemplate.global.auth.application.port.in.RefreshTokenUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.hacsick.jwttemplate.global.config.properties.JwtProperties;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService implements RefreshTokenUseCase {

    private final JwtProperties jwtProperties;

    private final TokenValidatePort tokenValidatePort;
    private final TokenProviderPortAdapter tokenProviderPort;

    public RefreshTokenService(final JwtProperties jwtProperties,
                               final TokenValidatePort tokenValidatePort,
                               final TokenProviderPortAdapter tokenProviderPort) {
        this.jwtProperties = jwtProperties;
        this.tokenValidatePort = tokenValidatePort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public AuthenticationResponse refreshToken(final HttpServletRequest httpServletRequest, final User user) {
        final String accessToken = this.extractTokenFromServlet(
                        httpServletRequest,
                        this.jwtProperties.getHeader(),
                        this.jwtProperties.getPrefix())
                .orElseThrow(IllegalArgumentException::new);

        if (this.tokenValidatePort.loadBlackListToken(accessToken).isPresent()) {
            throw new IllegalArgumentException();
        }

        final String email = tokenProviderPort.extractUsername(accessToken);

        if (!email.equals(user.getEmail())) {
            throw new IllegalArgumentException();
        }

        final Long remainingExpiredTime = this.tokenProviderPort.getRemainingExpiredTime(accessToken);
        if (remainingExpiredTime > 0) {
            this.tokenValidatePort.saveBlackList(accessToken, remainingExpiredTime);
        }
        this.tokenValidatePort.convertRefreshTokenToBlacklist(email); // RefreshToken To BlackList

        final String refreshToken = this.tokenProviderPort.generateRefreshToken(email);
        tokenValidatePort.saveRefreshToken(
                email,
                refreshToken,
                tokenProviderPort.getRemainingExpiredTime(refreshToken));

        return new AuthenticationResponse(tokenProviderPort.generateAccessToken(email));
    }
}
