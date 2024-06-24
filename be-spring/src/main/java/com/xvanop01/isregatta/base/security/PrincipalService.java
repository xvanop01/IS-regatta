package com.xvanop01.isregatta.base.security;

import com.xvanop01.isregatta.user.model.UserLogin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * PrincipalService
 * Servis pouzivany pre ziskavanie prihlaseneho pouzivatela
 * @author 2024 Peter Vano
 */
public class PrincipalService {

    /**
     * @return id aktualne prihlaseneho pouzivatela
     */
    public static Integer getPrincipalId() {
        return getPrincipal().getId();
    }

    /**
     * Ziska detail aktualne prihlaseneho pouzivatela
     * @return pouzavetelove informacie
     */
    private static UserLogin getPrincipal() {
        return (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
