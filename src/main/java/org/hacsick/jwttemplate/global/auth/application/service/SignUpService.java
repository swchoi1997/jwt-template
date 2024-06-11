package org.hacsick.jwttemplate.global.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.application.port.in.SignUpUseCase;
import org.hacsick.jwttemplate.global.auth.application.port.in.dto.SignUpCommand;
import org.hacsick.jwttemplate.global.auth.application.port.out.PasswordEncodePort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenProviderPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.hacsick.jwttemplate.global.user.application.port.out.LoadUserPort;
import org.hacsick.jwttemplate.global.user.application.port.out.SaveUserPort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.hacsick.jwttemplate.global.utils.time.CustomAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignUpService implements SignUpUseCase {

    private final PasswordEncodePort passwordEncodePort;

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;


    @Override
    public User signUp(final SignUpCommand signUpCommand) {
        CustomAssert.notNull(signUpCommand, new IllegalArgumentException());

        // 이미 가입 된 이메일인지 체크 (PK)
        this.validateEmail(signUpCommand.getEmail());

        final User signUpUser = User.withoutId(
                signUpCommand.getEmail(),
                signUpCommand.getName(),
                this.passwordEncodePort.encode(signUpCommand.getPassword()),
                signUpCommand.getPhoneNumber(),
                signUpCommand.getRole(),
                true
        );

        this.saveUserPort.save(signUpUser);

        return signUpUser;
    }

    private void validateEmail(final String email) {
        CustomAssert.isNull(
                this.loadUserPort.findUserFromEmail(email),
                new IllegalArgumentException("이미 가입 된 계정입니다."));;

    }
}
