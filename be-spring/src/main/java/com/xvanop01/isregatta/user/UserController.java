package com.xvanop01.isregatta.user;

import com.xvanop01.isregatta.api.dto.CreateUserDto;
import com.xvanop01.isregatta.api.dto.UserDetailDto;
import com.xvanop01.isregatta.api.user.UserControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController implements UserControllerApi {

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

    @Override
    public ResponseEntity<UserDetailDto> createUser(CreateUserDto createUserDto) {
        return null;
    }
}
