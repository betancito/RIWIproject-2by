package com.riwi.project.domain.enums;

import jakarta.servlet.http.PushBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.riwi.project.domain.enums.UserPermision.*;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ
            )
    ),
    USER(
           Set.of(
                   USER_READ,
                   USER_UPDATE
           )
    )
    ;
    @Getter
    private final Set<UserPermision> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
