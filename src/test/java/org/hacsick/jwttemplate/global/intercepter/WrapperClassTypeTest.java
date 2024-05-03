package org.hacsick.jwttemplate.global.intercepter;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.hacsick.jwttemplate.global.common.type.WrapperClassType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WrapperClassTypeTest {

    @ParameterizedTest(name = "type: {0}")
    @MethodSource("wrapperTypes")
    void wrapperTypeTest(final Class<?> clazz) {
        boolean wrapperType = WrapperClassType.isWrapperType(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader);
        Assertions.assertThat(wrapperType).isTrue();
    }

    static Stream<Arguments> wrapperTypes() {
        return Stream.of(
                arguments(Integer.class),
                arguments(Double.class),
                arguments(Float.class),
                arguments(Long.class),
                arguments(Short.class),
                arguments(Byte.class),
                arguments(Boolean.class),
                arguments(Character.class),
                arguments(LocalDateTime.class),
                arguments(LocalDate.class),
                arguments(Object.class),
                arguments(List.class)


        );
    }

    @ParameterizedTest(name = "type: {0}")
    @MethodSource("userDefineTypes")
    void userDefineTypeTest(final Class<?> clazz) {
        boolean wrapperType = WrapperClassType.isWrapperType(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader);
        Assertions.assertThat(wrapperType).isFalse();
    }

    static Stream<Arguments> userDefineTypes() {
        return Stream.of(
                arguments(Test1.class),
                arguments(MyInteger.class)
        );
    }

    static class Test1 {

    }
    static class MyInteger {

    }

}

