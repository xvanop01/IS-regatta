package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * UserPersistenceService
 * Spracovanie udajov o pouzivatelovi z DB
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
public class UserPersistenceService extends PersistenceService<User, UserRepository> {

    public UserPersistenceService(UserRepository repository) {
        super(repository);
    }

    public User findByUsername(String username) {
        log.info("findByUsername: {}", username);
        return repository.findByUsername(username).orElse(null);
    }
}
