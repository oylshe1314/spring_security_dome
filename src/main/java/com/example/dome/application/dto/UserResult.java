package com.example.dome.application.dto;


import com.example.dome.application.entity.User;

import java.io.Serializable;

public class UserResult implements Serializable {

    public long id;
    public String username;
    public String nickname;
    public String email;
    public String mobile;
    public String qq;
    public String regDt;
    public String avatar;

    public UserResult() {

    }

    public UserResult(User user) {
        this.id = user.id;
        this.username = user.username;
        this.nickname = user.nickname;
        this.email = user.email;
        this.mobile = user.mobile;
        this.qq = user.qq;
        this.regDt = user.regDt.toString();
        this.avatar = user.avatar;
    }
}
