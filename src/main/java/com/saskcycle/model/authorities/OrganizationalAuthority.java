package com.saskcycle.model.authorities;

import org.springframework.security.core.GrantedAuthority;

public class OrganizationalAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "Org";
    }
}
