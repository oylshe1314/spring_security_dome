package com.example.dome.application.auth;

import com.example.dome.application.entity.User;
import com.example.dome.application.service.UserService;
import com.example.dome.application.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Authentication authenticate(Authentication authRequest) throws AuthenticationException {

        String username = (String) authRequest.getPrincipal();
        String password = (String) authRequest.getCredentials();

        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        if (!passwordEncoder.matches(password, user.password)) {
            throw new BadCredentialsException("");
        }

        UserAuthDetails authDetails = new UserAuthDetails(user, null);
        UserAuthToken authResult = new UserAuthToken(authDetails, tokenUtils.generateToken(authDetails), null);

//        List<UserAuthority> authorities = userService.getAuthorities(user.id);
//        UserAuthDetails authDetails = new UserAuthDetails(user, authorities);
//        UserAuthToken authResult = new UserAuthToken(authDetails, authorities);

        authResult.setDetails(authRequest.getDetails());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthToken.class.isAssignableFrom(authentication);
    }
}
