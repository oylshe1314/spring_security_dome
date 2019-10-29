package com.example.dome.application.filter;

import com.example.dome.application.auth.UserAuthDetails;
import com.example.dome.application.auth.UserAuthToken;
import com.example.dome.application.entity.User;
import com.example.dome.application.service.UserService;
import com.example.dome.application.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            return token;
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                Long userId = tokenUtils.getUserId(token);

                User user = userService.getById(userId);
                if (user != null) {
                    UserAuthDetails userAuthDetails = new UserAuthDetails(user, null);

                    UserAuthToken authResult = new UserAuthToken(userAuthDetails, token, null);
                    authResult.setDetails(authenticationDetailsSource.buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }
}
