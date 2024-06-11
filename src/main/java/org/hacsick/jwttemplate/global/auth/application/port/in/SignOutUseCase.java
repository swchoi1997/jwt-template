package org.hacsick.jwttemplate.global.auth.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.http.HttpRequest;

public interface SignOutUseCase extends TokenExtractor{

    void signOut(final HttpServletRequest httpServletRequest, final User user);
}
