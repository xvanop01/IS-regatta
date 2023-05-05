package com.xvanop01.isregatta.base.security;

import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SecurityService {

    public static final String ROLE_ADMIN = "ADMIN";

    @Autowired
    private UserService userService;

    public boolean hasRole(String role) throws HttpException {
        List<Role> userRoles = userService.getUsersRoles(PrincipalService.getPrincipalId());
        for (Role r : userRoles) {
            if (r.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRoleOrIsUser(Integer userId, String role) throws HttpException {
        Integer loggedUserId = PrincipalService.getPrincipalId();
        if (loggedUserId.equals(userId)) {
            return true;
        }
        return hasRole(role);
    }
}
