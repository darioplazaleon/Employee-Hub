package com.example.employessytem.entity;

import static com.example.employessytem.entity.Permission.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public enum Role {
  ADMIN(
      Set.of(
          ADMIN_WRITE,
          ADMIN_READ,
          ADMIN_DELETE,
          ADMIN_UPDATE,
          MANAGER_DELETE,
          MANAGER_UPDATE,
          MANAGER_WRITE,
          MANAGER_READ)),
  MANAGER(Set.of(MANAGER_DELETE, MANAGER_UPDATE, MANAGER_WRITE, MANAGER_READ)),
  USER(Collections.emptySet());

  @Getter private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities =
        getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return authorities;
  }
}
