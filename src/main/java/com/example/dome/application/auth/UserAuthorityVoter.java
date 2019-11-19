package com.example.dome.application.auth;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public class UserAuthorityVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation invocation, Collection<ConfigAttribute> attributes) {
        if (!(authentication instanceof UserAuthToken)) {
            return ACCESS_ABSTAIN;
        }

        HttpServletRequest request = invocation.getHttpRequest();
        String uri = request.getRequestURI();

        UserAuthDetails userAuthDetails = (UserAuthDetails) authentication.getPrincipal();
        List<UserAuthority> authorities = userAuthDetails.getAuthorities();

        for (UserAuthority authority : authorities) {
            String path = authority.getAuthority();
            if (path.endsWith("/**")) {
                if (uri.startsWith(path.substring(0, path.length() - 3))) {
                    return ACCESS_GRANTED;
                }
            } else {
                if (path.equals(uri)) {
                    return ACCESS_GRANTED;
                }
            }
        }

        return ACCESS_DENIED;
    }
}
