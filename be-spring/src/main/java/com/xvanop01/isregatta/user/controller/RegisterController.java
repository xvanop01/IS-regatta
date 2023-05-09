package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpExceptionHandler httpExceptionHandler;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDetailDto> registerUser(String username, String password) {
        log.info("registerUser: {}", username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user = userService.createUser(user);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(201).body(userMapper.map(user));
    }


}
