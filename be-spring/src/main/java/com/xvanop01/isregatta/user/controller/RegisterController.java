package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * RegisterController
 * Zabezpecuje funkcionalitu registracie uzivatelov
 * @author 2024 Peter Vano
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Endpoint pre registraciu uzivatelov
     * @param username pouzivatelske meno
     * @param password heslo
     * @return detail zarigisrovaneho pouzivatela
     * @implNote mozne pridat rolu USER pri nechvalovani registracii
     */
    @PostMapping("/register")
    public ResponseEntity<UserDetailDto> registerUser(String username, String password) {
        log.info("registerUser: {}", username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            user = userService.createUser(user);
            // tu je mozne pridat rolu USER pre pouzivatela, ak nie je potrebne schvalovat registraciu
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.map(user));
    }


}
