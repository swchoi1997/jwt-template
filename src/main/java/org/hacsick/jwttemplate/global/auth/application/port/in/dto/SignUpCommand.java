package org.hacsick.jwttemplate.global.auth.application.port.in.dto;

import lombok.Getter;
import org.hacsick.jwttemplate.global.user.domain.Role;

@Getter
public class SignUpCommand {
    private final String email;
    private final String name;
    private final String password;
    private final String passwordConfirm;
    private final String phoneNumber;
    private final Role role;

    public SignUpCommand(final String email,
                         final String name,
                         final String password,
                         final String passwordConfirm,
                         final String phoneNumber,
                         final Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
