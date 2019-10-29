package com.example.dome.application.auth;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    private String path;

    public UserAuthority(String path) {
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return path;
    }
}
