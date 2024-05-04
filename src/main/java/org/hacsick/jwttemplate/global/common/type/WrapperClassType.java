package org.hacsick.jwttemplate.global.common.type;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WrapperClassType {
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    LONG(Long.class),
    SHORT(Short.class),
    BYTE(Byte.class),
    BOOLEAN(Boolean.class),
    CHARACTER(Character.class),
    STRING(String.class),
    ;

    private static final Set<Class<?>> WRAPPER_CLASS_TYPES = Collections.unmodifiableSet(
            Stream.of(values()).map(WrapperClassType::getClazzType).collect(Collectors.toSet())
    );

    private Class<?> clazzType;
    WrapperClassType(final Class<?> clazzType) {
        this.clazzType = clazzType;
    }

    public Class<?> getClazzType() {
        return clazzType;
    }

    public static boolean isWrapperType(Class<?> clazzType) {
        // List, Set Map 등 Collection 타입도 걸림
        return WRAPPER_CLASS_TYPES.contains(clazzType) || clazzType.getClassLoader() == null;
    }
}
