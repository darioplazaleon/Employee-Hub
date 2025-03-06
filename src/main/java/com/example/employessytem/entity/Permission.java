package com.example.employessytem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
  ADMIN_READ("admin:read"),
  ADMIN_WRITE("admin:write"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_DELETE("admin:delete"),

  MANAGER_READ("manager:read"),
  MANAGER_WRITE("manager:write"),
  MANAGER_UPDATE("manager:update"),
  MANAGER_DELETE("manager:delete"),

  USER_READ("user:read"),
  USER_WRITE("user:write"),
  USER_UPDATE("user:update"),
  USER_DELETE("user:delete");

  @Getter private final String permission;
}
