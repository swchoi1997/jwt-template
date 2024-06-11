package org.hacsick.jwttemplate.global.auth.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.auth.adapter.in.web.dto.AuthenticationResponse;
import org.hacsick.jwttemplate.global.user.domain.User;

public interface RefreshTokenUseCase extends TokenExtractor{

    AuthenticationResponse refreshToken(final HttpServletRequest httpServletRequest, final User user);


}
