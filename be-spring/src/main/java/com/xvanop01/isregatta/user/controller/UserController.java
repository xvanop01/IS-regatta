package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.dto.CreateUserDto;
import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.api.user.UserControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserController implements UserControllerApi {


    @Override
    public ResponseEntity<UserDetailDto> createUser(CreateUserDto createUserDto) {
        return ResponseEntity.badRequest().build();
    }
}
