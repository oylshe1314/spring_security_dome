package com.example.dome.application.filter;

import com.example.dome.application.auth.UserAuthToken;
import com.example.dome.application.common.RequestParameter;
import com.example.dome.application.dto.UserLogin;
import com.example.dome.application.util.ParameterUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public UserLoginFilter(String processesUrl) {
        super(processesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        RequestParameter<UserLogin> requestParameter = objectMapper.readValue(request.getInputStream(), new TypeReference<RequestParameter<UserLogin>>() {
        });

        try {

            UserLogin data = requestParameter.getDataNonNull();
            ParameterUtils.nonEmpty(data.username, data.password);

            UserAuthToken authRequest = new UserAuthToken(data.username, data.password);

            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            throw new BadCredentialsException("");
        }
    }
}
