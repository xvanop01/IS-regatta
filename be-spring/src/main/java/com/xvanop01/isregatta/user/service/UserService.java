package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.base.exception.Http400ReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserPersistanceService userPersistanceService;

    @Autowired
    private RolePersistenceService rolePersistenceService;

    public User getUserById(Integer userId) throws HttpException {
        log.info("getUserById: {}", userId);
        if (userId != null) {
            User user = userPersistanceService.findById(userId);
            if (user != null) {
                return user;
            }
        }
        throw new HttpException(Http400ReturnCode.NOT_FOUND, "User not found by id: " + userId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public User createUser(User user) throws HttpException {
        log.info("createUser: {}", user);
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty()) {
            throw new HttpException(Http400ReturnCode.BAD_REQUEST, "User must have username and password defined.");
        }
        return userPersistanceService.persist(user);
    }

    @Transactional(rollbackFor = HttpException.class)
    public User updateUser(Integer userId, User updateUser) throws HttpException {
        log.info("updateUser: userId : {}, updateUser : {}", userId, updateUser);
        if (userId == null || updateUser == null) {
            throw new HttpException(Http400ReturnCode.BAD_REQUEST, "Missing update user's info.");
        }
        User user = userPersistanceService.findById(userId);
        if (user == null) {
            throw new HttpException(Http400ReturnCode.NOT_FOUND, "User not found by id: " + userId);
        }
        if (updateUser.getUsername() != null && !updateUser.getUsername().isEmpty()) {
            User userByUsername = userPersistanceService.findByUsername(updateUser.getUsername());
            if (userByUsername != null) {
                throw new HttpException(Http400ReturnCode.CONFLICT,
                        String.format("User with username '%s' already exists.", updateUser.getUsername()));
            }
            user.setUsername(updateUser.getUsername());
        }
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            user.setPassword(updateUser.getPassword());
        }
        return userPersistanceService.persist(user);
    }

    @Transactional(rollbackFor = HttpException.class)
    public List<Role> setUsersRoles(Integer userId, List<Integer> rolesIds) throws HttpException {
        log.info("setUserRoles: userId: {}, rolesIds: {}", userId, rolesIds);
        User user = userId == null ? null : userPersistanceService.findById(userId);
        if (user == null) {
            throw new HttpException(Http400ReturnCode.NOT_FOUND, "User not found by id: " + userId);
        }
        List<Integer> userRolesIds = rolePersistenceService.getRolesByUserId(userId).stream().map(Role::getId).toList();
        List<Integer> allRolesIds = rolePersistenceService.getAllRoles().stream().map(Role::getId).toList();
        for (Integer id : userRolesIds) {
            if (!rolesIds.contains(id)) {
                rolePersistenceService.removeRoleFromUser(userId, id);
            }
        }
        for (Integer id : rolesIds) {
            if (!allRolesIds.contains(id)) {
                throw new HttpException(Http400ReturnCode.NOT_FOUND, "Role not found by id: " + id);
            }
            if (!userRolesIds.contains(id)) {
                rolePersistenceService.addRoleToUser(userId, id);
            }
        }
        return rolePersistenceService.getRolesByUserId(userId);
    }
}
