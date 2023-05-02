package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserPersistanceService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User persist(User user) {
        log.info("persist: {}", user);
        return repository.save(user);
    }

    public User findById(Integer userId) {
        log.info("findById: {}", userId);
        return repository.findById(userId).orElse(null);
    }

    public User findByUsername(String username) {
        log.info("findByUsername: {}", username);
        return repository.findByUsername(username).orElse(null);
    }
}
