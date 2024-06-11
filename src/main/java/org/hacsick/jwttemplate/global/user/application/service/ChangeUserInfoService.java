package org.hacsick.jwttemplate.global.user.application.service;

import org.hacsick.jwttemplate.global.user.application.port.in.ChangeUserInfoUseCase;
import org.hacsick.jwttemplate.global.user.application.port.in.command.ChangeUserInfoCommand;
import org.hacsick.jwttemplate.global.user.application.port.out.SaveUserPort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeUserInfoService implements ChangeUserInfoUseCase {

    private final SaveUserPort saveUserPort;

    public ChangeUserInfoService(final SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Override
    public void changeUserInfo(final User user, final ChangeUserInfoCommand changeUserInfoCommand) {
        saveUserPort.update(user.getEmail(),
                User.withoutId(user.getEmail(),
                        changeUserInfoCommand.getName(),
                        user.getPassword(),
                        user.getPhoneNumber(),
                        user.getRole(),
                        user.isEnabled()
                ));
    }

}
