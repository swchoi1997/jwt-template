package org.hacsick.jwttemplate.global.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.auth.jwt.token")
@Getter @Setter
public class JwtProperties {

    private String header;
    private String prefix;
}