package com.example.dome.application.service;

import com.example.dome.application.dto.ChangePassword;
import com.example.dome.application.dto.SignUp;
import com.example.dome.application.entity.User;
import com.example.dome.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public User getById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void signUp(SignUp parameter) throws Exception {
        if (userRepository.existsByUsername(parameter.username)) {
            throw new Exception("用户名已存在");
        }

        User user = new User();

        user.username = parameter.username;
        user.password = this.passwordEncoder.encode(parameter.password);
        user.nickname = parameter.nickname;

        userRepository.save(user);
    }

    public void changePassword(long id, ChangePassword parameter) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new Exception("请求异常");
        }

        User user = optional.get();

        if (!passwordEncoder.matches(parameter.oldPassword, user.password)) {
            throw new Exception("旧密码错误");
        }

        String newPassword = passwordEncoder.encode(parameter.newPassword);
        userRepository.updatePassword(id, newPassword);
    }
}
