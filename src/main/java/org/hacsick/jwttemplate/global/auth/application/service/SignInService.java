package org.hacsick.jwttemplate.global.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignInCommand;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignInUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.out.AuthenticationPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenProviderPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Transactional
public class SignInService implements SignInUseCase {

    private final TokenProviderPort tokenProviderPort;
    private final AuthenticationPort authenticatePort;
    private final TokenValidatePort tokenValidatePort;


    @Override
    public AuthenticationResponse signIn(final SignInCommand signInCommand) {
        final User user = this.authenticatePort.authenticate(signInCommand.getEmail(), signInCommand.getPassword());
        Assert.notNull(user, "User object cannot be null");

        final String email = user.getEmail();

        final String accessToken = tokenProviderPort.generateAccessToken(email);
        final String refreshToken = tokenProviderPort.generateRefreshToken(email);

        //Save Redis refreshToken
        tokenValidatePort.saveRefreshToken(
                email,
                refreshToken,
                tokenProviderPort.getRemainingExpiredTime(refreshToken));

        return new AuthenticationResponse(accessToken);
    }

}
