package org.hacsick.jwttemplate.global.auth.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SignInCommand {

    @NotEmpty
    @Email
    private final String email;

    @NotEmpty
    private final String password;

    public SignInCommand(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
