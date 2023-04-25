package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.dto.CreateUserDto;
import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.api.user.UserControllerApi;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController implements UserControllerApi {

    @Autowired
    public UserService userService;

    @Autowired
    public HttpExceptionHandler httpExceptionHandler;

    @Autowired
    public UserMapper userMapper;

    @Override
    public ResponseEntity<UserDetailDto> createUser(CreateUserDto createUserDto) {
        log.info("createUser: {}", createUserDto);
        User user = userMapper.map(createUserDto);
        try {
            user = userService.createUser(user);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(201).body(userMapper.map(user));
    }

    @Override
    public ResponseEntity<UserDetailDto> getUser(Integer userId) {
        log.info("getUser: {}", userId);
        User user;
        try {
            user = userService.getUserById(userId);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(userMapper.map(user));
    }

    @Override
    public ResponseEntity<UserDetailDto> updateUser(Integer userId, CreateUserDto createUserDto) {
        log.info("updateUser: userId: {}, createUserDto: {}", userId, createUserDto);
        User user = userMapper.map(createUserDto);
        try {
            user = userService.updateUser(userId, user);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(userMapper.map(user));
    }
}
