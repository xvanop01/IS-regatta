package com.xvanop01.isregatta.base.security;

import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.service.UserService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_ORGANIZER = "ORGANIZER";

    private final UserService userService;

    public void hasAnyRole(String... roles) throws HttpException {
        List<Role> userRoles = userService.getUsersRoles(PrincipalService.getPrincipalId());
        for (Role role : userRoles) {
            if (Arrays.stream(roles).anyMatch(r -> r.equals(role.getRole()))) {
                return;
            }
        }
        throw new HttpException(HttpReturnCode.FORBIDDEN, "You don't have permission.");
    }

    public void hasRole(String role) throws HttpException {
        List<Role> userRoles = userService.getUsersRoles(PrincipalService.getPrincipalId());
        for (Role r : userRoles) {
            if (r.getRole().equals(role)) {
                return;
            }
        }
        throw new HttpException(HttpReturnCode.FORBIDDEN, "You don't have permission.");
    }

    public void hasRoleOrIsUser(Integer userId, String role) throws HttpException {
        Integer loggedUserId = PrincipalService.getPrincipalId();
        if (loggedUserId.equals(userId)) {
            return;
        }
        hasRole(role);
    }
}
