package org.hacsick.jwttemplate.global.auth.application.port.in.dto;

public class SignInCommand {

    private final String email;

    private final String password;

    public SignInCommand(final String email, final String password) {
        this.validate(email, password);

        this.email = email;
        this.password = password;
    }

    private void validate(final String email, final String password) {
        if (email.isEmpty() || email == null) {
            throw new IllegalArgumentException();
        }

        if (password.isEmpty() || password == null) {
            throw new IllegalArgumentException();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
