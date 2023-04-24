package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.model.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPersistanceService userPersistanceService;
    private final RolePersistenceService rolePersistenceService;

    public UserDetailsServiceImpl(UserPersistanceService userPersistanceService,
                                  RolePersistenceService rolePersistenceService) {
        super();
        this.userPersistanceService = userPersistanceService;
        this.rolePersistenceService = rolePersistenceService;
    }

    @Override
    public UserLogin loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: {}", username);
        User user = userPersistanceService.findByUsername(username);
        if (user != null) {
            UserLogin userLogin = new UserLogin(user.getUsername(), user.getPassword(), true, true, true, true,
                    getAuthorities(user.getId()));
            userLogin.setId(user.getId());
            return userLogin;
        }
        throw new UsernameNotFoundException("User not found by username: " + username);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Integer userId) {
        log.info("getAuthorities: {}", userId);
        return rolePersistenceService.getRolesByUserId(userId).stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
