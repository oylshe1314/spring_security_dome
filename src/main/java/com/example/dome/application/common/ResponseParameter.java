package com.example.dome.application.common;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseParameter<T> {

    public boolean success;
    public String message;
    public T result;

    public static ResponseParameter fail(String message) {
        ResponseParameter response = new ResponseParameter();
        response.success = false;
        response.message = message;
        return response;
    }


    public static ResponseParameter success() {
        ResponseParameter response = new ResponseParameter();
        response.success = true;
        response.message = "success";
        return response;
    }

    public static <T> ResponseParameter<T> success(T result) {
        ResponseParameter<T> response = new ResponseParameter<>();
        response.success = true;
        response.message = "success";
        response.result = result;
        return response;
    }
}
