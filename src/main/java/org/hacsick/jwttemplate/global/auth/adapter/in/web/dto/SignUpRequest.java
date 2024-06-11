package org.hacsick.jwttemplate.global.auth.adapter.in.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hacsick.jwttemplate.global.auth.application.port.in.dto.SignUpCommand;
import org.hacsick.jwttemplate.global.user.domain.Role;

@Getter
public class SignUpRequest {

    @NotEmpty
    private final String email;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String password;
    @NotEmpty
    private final String passwordConfirm;
    @NotEmpty
    private final String phoneNumber;

    private final Role role;

    public SignUpRequest(final String email,
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


    public SignUpCommand toCommand() {
        return new SignUpCommand(
                this.email,
                this.name,
                this.password,
                this.passwordConfirm,
                this.phoneNumber,
                this.role);
    }
}
