package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * RolePersistenceService
 * Spracovanie udajov o roliach z DB
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
public class RolePersistenceService extends PersistenceService<Role, RoleRepository> {

    public RolePersistenceService(RoleRepository repository) {
        super(repository);
    }

    /**
     * Ziska vsetky role pouzivatela
     * @param userId id pouzivatela
     * @return zoznam roli
     */
    public List<Role> getRolesByUserId(Integer userId) {
        log.info("getRolesByUserId: {}", userId);
        if (userId != null) {
            return repository.findAllByUserId(userId);
        }
        return new ArrayList<>();
    }

    /**
     * Ziska vsetky role pouzivatela
     * @param username pouzivatelske meno
     * @return zoznam roli
     */
    public List<Role> getRolesByUsername(String username) {
        log.info("getRolesByUsername: {}", username);
        if (username != null) {
            return repository.findAllByUsername(username);
        }
        return new ArrayList<>();
    }

    @Transactional
    public void addRoleToUser(Integer userId, Integer roleId) {
        log.info("addRoleToUser: userId: {}, roleId: {}", userId, roleId);
        if (userId != null && roleId != null) {
            repository.addRoleToUser(userId, roleId);
        }
    }

    @Transactional
    public void removeRoleFromUser(Integer userId, Integer roleId) {
        log.info("removeRoleFromUser: userId: {}, roleId: {}", userId, roleId);
        if (userId != null && roleId != null) {
            repository.removeRoleFromUser(userId, roleId);
        }
    }
}
