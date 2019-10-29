package com.example.dome.application.handler;

import com.example.dome.application.common.ResponseParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class UserLogoutHandler extends CustomRestHandler implements LogoutHandler, LogoutSuccessHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (SecurityContextHolder.getContext().getAuthentication() == authentication) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        SecurityContextHolder.clearContext();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        responseJson(response, ResponseParameter.success());
    }
}