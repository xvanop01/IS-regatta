package com.xvanop01.isregatta.base.security;

import com.xvanop01.isregatta.user.model.UserLogin;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalService {

    public static Integer getPrincipalId() {
        return getPrincipal().getId();
    }

    private static UserLogin getPrincipal() {
        return (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
