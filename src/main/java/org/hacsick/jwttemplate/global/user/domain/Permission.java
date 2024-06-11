package org.hacsick.jwttemplate.global.user.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Permission {
    READ("R"),
    WRITE("W"),
    DELETE("D"),
    NONE("NONE");

    public static final Map<String, Permission> PERMISSION_MAP = Collections
            .unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Permission::getPermission, Function.identity())));

    private final String permission;

    Permission(final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
