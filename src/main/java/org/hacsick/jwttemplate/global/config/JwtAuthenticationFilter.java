package org.hacsick.jwttemplate.global.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenProviderPort;
import org.hacsick.jwttemplate.global.auth.application.port.out.TokenValidatePort;
import org.hacsick.jwttemplate.global.config.properties.JwtProperties;
import org.hacsick.jwttemplate.global.user.application.port.out.LoadUserPort;
import org.hacsick.jwttemplate.global.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Value("${application.auth.jwt.token.header}")
//    private String tokenHeader;
//
//    @Value("${application.auth.jwt.token.prefix}")
//    private String tokenPrefix;


    private final JwtProperties jwtProperties;
    private final LoadUserPort loadUserPort;
    private final TokenValidatePort tokenValidatePort;
    private final TokenProviderPort tokenProviderPort;

    public JwtAuthenticationFilter(final JwtProperties jwtProperties, final LoadUserPort loadUserPort,
                                   final TokenValidatePort tokenValidatePort,
                                   final TokenProviderPort tokenProviderPort) {
        this.jwtProperties = jwtProperties;
        this.loadUserPort = loadUserPort;
        this.tokenValidatePort = tokenValidatePort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)  throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String tokenHeader = request.getHeader(this.jwtProperties.getHeader());
        if (!this.assertTokenHeader(tokenHeader)){
            throw new JwtException("Token Header Not Found");
        }

        final String accessToken = tokenHeader.substring(this.jwtProperties.getPrefix().length());

        if (StringUtils.hasText(accessToken)) {
            if (tokenValidatePort.loadBlackListToken(accessToken).isPresent() || !tokenProviderPort.isValidToken(accessToken)){
                throw new JwtException("Token Expired");
            }

            //If Token is Valid -> getUser
            final User user = loadUserPort.findUserFromEmail(tokenProviderPort.extractUsername(accessToken))
                    .orElseThrow(() -> new UsernameNotFoundException(""));

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private boolean assertTokenHeader(final String tokenHeader) {
//        if (tokenHeader == null || !tokenHeader.startsWith(this.tokenPrefix)){
        if (tokenHeader == null || !tokenHeader.startsWith(this.jwtProperties.getPrefix())){
            log.warn("No bearer token found in header");
            return false; // 토큰이 없을 시, Security Filter Chain을 실행시키지 않음
        }
        return true;
    }
}
