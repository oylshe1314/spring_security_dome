package com.example.dome.application.handler;

import com.example.dome.application.common.ResponseParameter;
import com.example.dome.application.dto.UserLoginResult;
import com.example.dome.application.dto.UserResult;
import com.example.dome.application.entity.User;
import com.example.dome.application.util.VariableCheck;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestLoginHandler extends CustomRestHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        responseJson(response, ResponseParameter.success(new UserResult(user)));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            responseJson(response, ResponseParameter.fail(-1, "用户名或者密码错误"));
        } else {
            String message = null;
            Throwable t = exception;
            while (t != null) {
                message = t.getMessage();
                if (VariableCheck.notEmpty(message)) {
                    responseJson(response, ResponseParameter.fail(-1, message));
                    break;
                }

                t = exception.getCause();
            }

            if (!VariableCheck.notEmpty(message)) {
                responseJson(response, ResponseParameter.fail(-1, "请求异常"));
            }
        }
    }
}