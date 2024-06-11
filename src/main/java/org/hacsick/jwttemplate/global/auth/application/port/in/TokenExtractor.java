package org.hacsick.jwttemplate.global.auth.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenExtractor {

    default Optional<String> extractTokenFromServlet(final HttpServletRequest request,
                                                     final String header,
                                                     final String prefix) {
        final String headerValue = request.getHeader(header);
        if (headerValue == null || !headerValue.startsWith(prefix)) {
            return Optional.empty();
        }
        return Optional.of(headerValue.substring(prefix.length()));
    }
}
