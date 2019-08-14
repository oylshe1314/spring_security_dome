package com.example.dome.application.controller;

import com.example.dome.application.common.RequestParameter;
import com.example.dome.application.common.ResponseParameter;
import com.example.dome.application.dto.UserChangePasswordParameter;
import com.example.dome.application.dto.UserSignUpParameter;
import com.example.dome.application.entity.User;
import com.example.dome.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseParameter signUp(@RequestBody RequestParameter<UserSignUpParameter> requestParameter) throws Exception {
        if (requestParameter.data == null) {
            throw new Exception("参数错误");
        }
        userService.signUp(requestParameter.data);
        return ResponseParameter.success();
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseParameter changePassword(@RequestBody RequestParameter<UserChangePasswordParameter> requestParameter, Authentication authentication) throws Exception {
        if (requestParameter.data == null) {
            throw new Exception("参数错误");
        }

        User user = (User) authentication.getPrincipal();

        userService.changePassword(user.id, requestParameter.data);
        return ResponseParameter.success();
    }
}
