package org.hacsick.jwttemplate.global.utils.time;

import java.util.Optional;
import org.springframework.util.Assert;

public abstract class CustomAssert extends Assert {

    public CustomAssert() {
        throw new UnsupportedOperationException();
    }

    public static void ifFalse(final boolean expression,
                               final RuntimeException e) {
        if (!expression) {
            throw e;
        }
    }

    public static void ifFalse(final boolean expression,
                               final Runnable runnable) {
        if (!expression) {
            runnable.run();
        }
    }

    public static void notNull(final Object o,
                               final RuntimeException e) {
        if (o == null) {
            throw e;
        }
    }

    public static void isNull(final Object o,
                               final RuntimeException e) {

        if (o instanceof Optional<?>) {
            if (!((Optional<?>) o).isEmpty()) {
                throw e;
            }
            return;
        }
        if (o != null) {
            throw e;
        }
    }



}
