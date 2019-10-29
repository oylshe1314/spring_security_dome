package com.example.dome.application.auth;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Component
public class UserAuthorityManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String uri = request.getRequestURI();

        UserAuthDetails userAuthDetails = (UserAuthDetails) authentication.getPrincipal();
        List<UserAuthority> authorities = userAuthDetails.getAuthorities();

        for (UserAuthority authority : authorities) {
            String path = authority.getAuthority();
            if (path.endsWith("/**")) {
                if (uri.startsWith(path.substring(0, path.length() - 3))) {
                    return;
                }
            } else {
                if (path.equals(uri)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
