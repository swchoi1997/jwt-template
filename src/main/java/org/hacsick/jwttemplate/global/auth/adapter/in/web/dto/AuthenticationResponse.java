package org.hacsick.jwttemplate.global.auth.adapter.in.web.dto;

public class AuthenticationResponse {

    private final String accessToken;

    public AuthenticationResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
