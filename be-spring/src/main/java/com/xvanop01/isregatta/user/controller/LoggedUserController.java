package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.service.UserPersistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public class LoggedUserController {

    @Autowired
    private UserPersistanceService userPersistanceService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public UserDetailDto currentUserDetail(Principal principal) {
        return userMapper.map(userPersistanceService.findByUsername(principal.getName()));
    }
}