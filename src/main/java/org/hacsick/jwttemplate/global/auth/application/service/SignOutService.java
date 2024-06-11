package org.hacsick.jwttemplate.global.auth.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignOutUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenProviderPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.hacsick.jwttemplate.global.config.properties.JwtProperties;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignOutService implements SignOutUseCase {

    private final JwtProperties jwtProperties;
    private final TokenProviderPort tokenProviderPort;
    private final TokenValidatePort tokenValidatePort;

    public SignOutService(final JwtProperties jwtProperties,
                          final TokenProviderPort tokenProviderPort,
                          final TokenValidatePort tokenValidatePort) {
        this.jwtProperties = jwtProperties;
        this.tokenProviderPort = tokenProviderPort;
        this.tokenValidatePort = tokenValidatePort;
    }

    @Override
    public void signOut(final HttpServletRequest httpServletRequest, final User user) {
        final String accessToken = this.extractTokenFromServlet(
                        httpServletRequest,
                        this.jwtProperties.getHeader(),
                        this.jwtProperties.getPrefix())
                .orElseThrow(IllegalArgumentException::new)
                .trim();

        if (!tokenProviderPort.isValidToken(accessToken, user.getEmail())) {
            throw new IllegalArgumentException();
        }
        this.tokenValidatePort.saveBlackList(accessToken, this.tokenProviderPort.getRemainingExpiredTime(accessToken));
        this.tokenValidatePort.convertRefreshTokenToBlacklist(user.getEmail()); // RefreshToken To BlackList
    }
}
