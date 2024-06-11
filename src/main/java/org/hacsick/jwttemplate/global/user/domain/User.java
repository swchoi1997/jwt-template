package org.hacsick.jwttemplate.global.user.domain;

import java.util.Collection;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hacsick.jwttemplate.global.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public final class User extends BaseEntity {
    private static final String WITHDRAW = "withdrawn";

    private final Long id;

    private final String email;

    private final String name;

    private final String password;

    private final String phoneNumber;

    private final Role role;

    private final boolean enabled;

    public static User withInIsEnable(final User user, final boolean enabled) {
        return new User(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getRole(),
                enabled);
    }

    public static User withoutId(final String email,
                                 final String name,
                                 final String password,
                                 final String phoneNumber,
                                 final Role role,
                                 final boolean enabled) {
        return new User(null, email, name, password, phoneNumber, role, enabled);
    }

    public static User ofWithDraw() {
        return User.withoutId(WITHDRAW, WITHDRAW, WITHDRAW, WITHDRAW, Role.NONE, false);
    }

    private User(final Long id,
                 final String email,
                 final String name,
                 final String password,
                 final String phoneNumber,
                 final Role role,
                 final boolean enabled) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.enabled = enabled;
    }

    public User changeEnabled(final boolean enabled) {
        return User.withInIsEnable(this, enabled);
    }


    /*
    PK, Email, Password 가 같으면 같은 유저임
     */
    public User withdraw() {
        return User.ofWithDraw();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
    }
}
