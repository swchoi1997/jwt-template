package org.hacsick.jwttemplate.global.config;

import org.hacsick.jwttemplate.global.auth.application.port.out.PasswordEncodePort;
import org.hacsick.jwttemplate.global.user.application.port.out.LoadUserPort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * 사용자의 자격 증명을 확인하고 인증 과정을 관리하는 클래스
 * 인증 후에는 새로운 Authentication 객체를 반환한다.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final LoadUserPort loadUserPort;

    private final PasswordEncodePort passwordEncodePort;

    public CustomAuthenticationProvider(final LoadUserPort loadUserPort,
                                        final PasswordEncodePort passwordEncodePort) {
        this.loadUserPort = loadUserPort;
        this.passwordEncodePort = passwordEncodePort;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String loginId = authentication.getName();
        final String password = authentication.getCredentials().toString();

        User user = loadUserPort.findUserFromEmail(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Assert.isTrue(passwordEncodePort.isMatch(password, user.getPassword()), "Password Not Matches");

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
