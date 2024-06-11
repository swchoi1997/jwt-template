package org.hacsick.jwttemplate.global.auth.adapter.in.web.validator;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AuthPatternType {
    USERNAME("[a-zA-Z0-9]{2,15}"),
    PASSWORD("^(?=.*\\d)(?=.*[0-9a-zA-Z])(?=.*[~!@#$%^&*()=+])[0-9a-zA-Z\\d~!@#$%^&*()=+]{8,20}$"),
    NAME("[가-힣]{2,10}"),
    EMAIL("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$"),
    NICKNAME("^[가-힣|a-z|A-Z|0-9|]{4,10}"),
    PHONE_NUMBER("^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$")
    ;

    public static final Map<AuthPatternType, Pattern> AUTH_PATTERN = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(Function.identity(), pattern -> Pattern.compile(pattern.getPattern())))
    );

    public static boolean match(final AuthPatternType patternType, final String var1) {
        return AUTH_PATTERN.get(patternType)
                .matcher(var1)
                .matches();
    }

    private final String pattern;

    AuthPatternType(final String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
