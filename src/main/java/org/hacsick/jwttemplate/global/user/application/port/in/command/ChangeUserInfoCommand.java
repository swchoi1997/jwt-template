package org.hacsick.jwttemplate.global.user.application.port.in.command;

import org.hacsick.jwttemplate.global.auth.adapter.in.web.validator.AuthPatternType;

public class ChangeUserInfoCommand {

    private final String name;

    public ChangeUserInfoCommand(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (!AuthPatternType.match(AuthPatternType.NAME, name)) {
            throw new IllegalArgumentException();
        }

    }

    public String getName() {
        return name;
    }
}
