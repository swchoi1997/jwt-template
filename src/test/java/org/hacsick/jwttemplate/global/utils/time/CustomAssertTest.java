package org.hacsick.jwttemplate.global.utils.time;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

class CustomAssertTest {

    @Test
    void isTrue() {
        RuntimeException e = new JwtException("test");
        boolean expression = 1 == 2;
        if (!expression) {
            throw e;
        }
    }
}