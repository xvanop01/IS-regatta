package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.service.UserPersistanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/api")
@Slf4j
public class LoggedUserController {

    @Autowired
    private UserPersistanceService userPersistanceService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserDetailDto> currentUserDetail(Principal principal) {
        log.info("currentUserDetail");
        if (principal == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(userMapper.map(userPersistanceService.findByUsername(principal.getName())));
    }
}