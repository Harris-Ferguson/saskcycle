package com.saskcycle.model.authorities;

import org.springframework.security.core.GrantedAuthority;

public class ModeratorAuthority implements GrantedAuthority {
  @Override
  public String getAuthority() {
    return "Mod";
  }
}
