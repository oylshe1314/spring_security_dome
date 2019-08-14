package com.example.dome.application.controller;

import com.example.dome.application.common.ResponseParameter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseParameter handler(Exception exception) {
        return ResponseParameter.fail(-1, "请求异常");
    }
}
