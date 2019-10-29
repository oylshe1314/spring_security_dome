package com.example.dome.application.controller;

import com.example.dome.application.common.RequestParameter;
import com.example.dome.application.common.ResponseParameter;
import com.example.dome.application.dto.ChangePassword;
import com.example.dome.application.dto.SignUp;
import com.example.dome.application.entity.User;
import com.example.dome.application.service.UserService;
import com.example.dome.application.util.ParameterUtils;
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

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseParameter signUp(@RequestBody RequestParameter<SignUp> requestParameter) throws Exception {
        SignUp data = requestParameter.getDataNonNull();
        ParameterUtils.nonEmpty(data.username, data.password, data.username);

        userService.signUp(requestParameter.data);

        return ResponseParameter.success();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseParameter changePassword(@RequestBody RequestParameter<ChangePassword> requestParameter, Authentication authentication) throws Exception {
        ChangePassword data = requestParameter.getDataNonNull();
        ParameterUtils.nonEmpty(data.oldPassword, data.newPassword);

        User user = (User) authentication.getPrincipal();

        userService.changePassword(user.id, requestParameter.data);
        return ResponseParameter.success();
    }
}
