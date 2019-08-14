package com.example.dome.application.filter;

import com.example.dome.application.common.RequestParameter;
import com.example.dome.application.dto.UserLoginParameter;
import com.example.dome.application.util.VariableCheck;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestParameter<UserLoginParameter> requestParameter = objectMapper.readValue(request.getInputStream(), new TypeReference<RequestParameter<UserLoginParameter>>() {
            });
            if (!VariableCheck.notEmpty(requestParameter.data.username, requestParameter.data.password)) {
                throw new BadCredentialsException("");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestParameter.data.username, requestParameter.data.password);
            setDetails(request, authenticationToken);
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("", e);
        }
    }
}
