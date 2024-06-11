package org.hacsick.jwttemplate.global.user.application.port.in;

import org.hacsick.jwttemplate.global.user.domain.User;

public interface WithdrawUserUseCase {
    void withdraw(User user);
}
