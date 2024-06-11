package org.hacsick.jwttemplate.global.user.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    ADMIN("ADMIN", Set.of(Permission.READ, Permission.WRITE, Permission.DELETE)),
    MANAGER("MANAGER", Set.of(Permission.READ, Permission.WRITE)),
    USER("USER", Set.of(Permission.NONE)),
    NONE("NONE", Set.of(Permission.NONE));
    private static final String ROLE_PREFIX = "ROLE_";

    public static final Map<Role, String> ROLE_MAP = Collections
            .unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Function.identity(), r -> ROLE_PREFIX + r.getRole()))
            );

    private final String role;

    private final Set<Permission> permissions;


    Role(final String role, final Set<Permission> permissions) {
        this.role = role;
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + this.name()));

        return authorities;
    }
}
