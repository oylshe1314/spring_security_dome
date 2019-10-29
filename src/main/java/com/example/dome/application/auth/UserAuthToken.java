package com.example.dome.application.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthToken extends AbstractAuthenticationToken {

    private Object principal;
    private Object credentials;

    public UserAuthToken(String username, String password) {
        super(null);
        this.principal = username;
        this.credentials = password;
        this.setAuthenticated(false);
    }

    public UserAuthToken(UserAuthDetails authDetails, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = authDetails;
        this.credentials = token;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return principal;
    }

    @Override
    public Object getPrincipal() {
        return credentials;
    }
}
