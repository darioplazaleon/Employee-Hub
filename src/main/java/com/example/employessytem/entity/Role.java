package com.example.employessytem.entity;

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
          Permission.ADMIN_READ,
          Permission.ADMIN_WRITE,
          Permission.ADMIN_UPDATE,
          Permission.ADMIN_DELETE,
          Permission.MANAGER_READ,
          Permission.MANAGER_WRITE,
          Permission.MANAGER_UPDATE,
          Permission.MANAGER_DELETE)),
  MANAGER(
      Set.of(
          Permission.MANAGER_READ,
          Permission.MANAGER_WRITE,
          Permission.MANAGER_UPDATE,
          Permission.MANAGER_DELETE)),
  USER(
      Set.of(
          Permission.USER_READ,
          Permission.USER_WRITE,
          Permission.USER_UPDATE,
          Permission.USER_DELETE));

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
