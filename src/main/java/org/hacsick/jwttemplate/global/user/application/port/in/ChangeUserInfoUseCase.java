package org.hacsick.jwttemplate.global.user.application.port.in;

import org.hacsick.jwttemplate.global.user.adapter.in.web.dto.ChangeUserInfoRequest;
import org.hacsick.jwttemplate.global.user.application.port.in.command.ChangeUserInfoCommand;
import org.hacsick.jwttemplate.global.user.domain.User;

public interface ChangeUserInfoUseCase {

    void changeUserInfo(final User user, final ChangeUserInfoCommand changeUserInfoCommand);


}
