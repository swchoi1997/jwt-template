package org.hacsick.jwttemplate.global.user.application.service;

import org.hacsick.jwttemplate.global.user.application.port.in.WithdrawUserUseCase;
import org.hacsick.jwttemplate.global.user.application.port.out.SaveUserPort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WithdrawUserService implements WithdrawUserUseCase {

    private final SaveUserPort saveUserPort;

    public WithdrawUserService(final SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }


    @Override
    public void withdraw(final User user) {
        User withdrawnUser = user.withdraw();
        saveUserPort.update(user.getEmail(), withdrawnUser);
    }
}
