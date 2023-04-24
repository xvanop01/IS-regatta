package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RolePersistenceService {

    @Autowired
    private RoleRepository repository;

    List<Role> getRolesByUserId(Integer userId) {
        if (userId != null) {
            return repository.findAllByUserId(userId);
        }
        return new ArrayList<>();
    }
}
