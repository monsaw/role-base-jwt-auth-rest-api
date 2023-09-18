package com.hommies.userauthwithjwt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public enum Role {
   // USER, ADMIN, MANAGER
    USER(Collections.EMPTY_SET),
    ADMIN(Set.of(
            Permission.ADMIN_DELETE,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_READ,
            Permission.MANAGEMENT_CREATE,
            Permission.MANAGEMENT_UPDATE,
            Permission.MANAGEMENT_DELETE,
            Permission.MANAGEMENT_READ
    )),

    MANAGER(Set.of(
            Permission.MANAGEMENT_CREATE,
            Permission.MANAGEMENT_UPDATE,
            Permission.MANAGEMENT_DELETE,
            Permission.MANAGEMENT_READ
    ));

    @Getter
    private final Set<Permission> permissions;

    public final List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities
                .add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
