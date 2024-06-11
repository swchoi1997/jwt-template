package org.hacsick.jwttemplate.global.auth.application.service;

import static org.hacsick.jwttemplate.global.user.domain.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignUpUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.in.dto.SignUpCommand;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpServiceTest {

    @Autowired
    SignUpUseCase signUpUseCase;

    @Test
    void signUpTest() {
        String mail = UUID.randomUUID() +  "@example.com";
        User user = signUpUseCase.signUp(
                new SignUpCommand(
                        mail,
                        "admin",
                        "oracle",
                        "oracle",
                        "010-1234-5678",
                        ADMIN)
        );

        Assertions.assertThat(user.getEmail()).isEqualTo(mail);
    }

    @Test
    void duplicateUserTest() {
        String mail = UUID.randomUUID() +  "@example.com";
        SignUpCommand signUpCommand = new SignUpCommand(
                mail,
                "admin",
                "oracle",
                "oracle",
                "010-1234-5678",
                ADMIN);

        signUpUseCase.signUp(signUpCommand);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> signUpUseCase.signUp(signUpCommand));
    }
}