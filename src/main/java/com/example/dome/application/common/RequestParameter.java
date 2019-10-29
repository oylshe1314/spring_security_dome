package com.example.dome.application.common;

public class RequestParameter<T> {

    public String caller;
    public String version;
    public T data;

    public T getDataNonNull() throws Exception {
        if (data == null) {
            throw new Exception("参数错误");
        }
        return data;
    }
}
