package tech.itpark.framework.security;

import tech.itpark.app.model.Role;

import java.util.Arrays;
import java.util.List;

public interface Auth {
    Role ROLE_ANONYMOUS = new Role(666L, "ROLE_ANONYMOUS");

    long getId();

    List<Role> getRoles();

    default boolean hasRole(Role role) {
        return hasAnyRole(role);
    }

    default boolean hasAnyRole(Role... roles) {
        return Arrays.stream(roles)
                .anyMatch(o -> getRoles().contains(o));
    }

    static Auth anonymous() {
        return new Auth() {
            @Override
            public long getId() {
                return -1;
            }

            @Override
            public List<Role> getRoles() {
                return List.of(ROLE_ANONYMOUS);
            }
        };
    }

    default boolean isAnonymous() {
        return hasRole(ROLE_ANONYMOUS);
    }
}
