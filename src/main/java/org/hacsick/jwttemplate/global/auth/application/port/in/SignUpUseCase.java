package org.hacsick.jwttemplate.global.auth.application.port.in;

import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.application.port.in.dto.SignUpCommand;
import org.hacsick.jwttemplate.global.user.domain.User;

public interface SignUpUseCase {

    User signUp(final SignUpCommand signUpCommand);


}
