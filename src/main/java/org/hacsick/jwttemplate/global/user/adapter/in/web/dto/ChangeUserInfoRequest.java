package org.hacsick.jwttemplate.global.user.adapter.in.web.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hacsick.jwttemplate.global.user.application.port.in.command.ChangeUserInfoCommand;

public class ChangeUserInfoRequest {
    @NotEmpty
    private final String name;

    public ChangeUserInfoRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ChangeUserInfoCommand toCommand() {
        return new ChangeUserInfoCommand(name);
    }
}
