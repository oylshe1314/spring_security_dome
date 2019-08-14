package com.example.dome.application.service;

import com.example.dome.application.dto.UserChangePasswordParameter;
import com.example.dome.application.dto.UserSignUpParameter;
import com.example.dome.application.entity.User;
import com.example.dome.application.repository.UserRepository;
import com.example.dome.application.util.VariableCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = this.userRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        return optional.get();
    }

    public void signUp(UserSignUpParameter parameter) throws Exception {
        if (!VariableCheck.notEmpty(parameter.username, parameter.password, parameter.nickname)) {
            throw new Exception("参数错误");
        }

        if (userRepository.existsByUsername(parameter.username)) {
            throw new Exception("用户名已存在");
        }

        User user = new User();

        user.username = parameter.username;
        user.password = this.passwordEncoder.encode(parameter.password);
        user.nickname = parameter.nickname;

        userRepository.save(user);
    }

    public void changePassword(long id, UserChangePasswordParameter parameter) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new Exception("请求异常");
        }

        if (!VariableCheck.notEmpty(parameter.oldPassword, parameter.newPassword)) {
            throw new Exception("参数错误");
        }

        User user = optional.get();

        if (!passwordEncoder.matches(parameter.oldPassword, user.password)) {
            throw new Exception("旧密码错误");
        }

        String newPassword = passwordEncoder.encode(parameter.newPassword);
        userRepository.updatePassword(id, newPassword);
    }
}
