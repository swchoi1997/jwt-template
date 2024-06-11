package org.hacsick.jwttemplate.global.auth.application.port.in;

import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.SignInCommand;

public interface SignInUseCase {

    AuthenticationResponse signIn(final SignInCommand signInCommand);


}
