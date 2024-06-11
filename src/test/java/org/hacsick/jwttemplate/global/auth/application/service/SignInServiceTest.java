package org.hacsick.jwttemplate.global.auth.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignInCommand;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignInUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenProviderPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignInServiceTest {

    @Autowired
    SignInUseCase signInUseCase;

    @Autowired
    TokenValidatePort tokenValidatePort;

    @Autowired
    TokenProviderPort tokenProviderPort;

    @Test
    void signInTest() {
        String email = "admin1@example.com";
        AuthenticationResponse authenticationResponse = signInUseCase.signIn(
                new SignInCommand(email, "oracle")
        );

        String accessToken = authenticationResponse.getAccessToken();
        String username = tokenProviderPort.extractUsername(accessToken);
        String refreshToken = tokenValidatePort.loadRefreshToken(username);

        Assertions.assertThat(email).isEqualTo(username);
        Assertions.assertThat(refreshToken).isNotNull();

        System.out.println(accessToken);

    }

}