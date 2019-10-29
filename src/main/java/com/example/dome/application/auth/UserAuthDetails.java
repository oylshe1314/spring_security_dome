package com.example.dome.application.auth;

import com.example.dome.application.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserAuthDetails implements UserDetails {

    private User user;
    private List<UserAuthority> authorities;

    public UserAuthDetails(User user, List<UserAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public List<UserAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.password;
    }

    @Override
    public String getUsername() {
        return user.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
