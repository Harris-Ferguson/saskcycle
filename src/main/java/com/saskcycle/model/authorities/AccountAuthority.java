package com.saskcycle.model.authorities;

import org.springframework.security.core.GrantedAuthority;

public class AccountAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "Account";
    }
}
