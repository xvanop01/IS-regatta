package com.xvanop01.isregatta.user.service;

import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * UserService
 * Servis zabezpecujuci aplikacnu logiku pre pouzivatelov
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserPersistenceService userPersistenceService;
    private final RolePersistenceService rolePersistenceService;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Integer userId) throws HttpException {
        log.info("getUserById: {}", userId);
        if (userId != null) {
            User user = userPersistenceService.findById(userId);
            if (user != null) {
                return user;
            }
        }
        throw new HttpException(HttpReturnCode.NOT_FOUND, "User not found by id: " + userId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public User createUser(User user) throws HttpException {
        log.info("createUser: {}", user);
        if (user == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "User is not defined.");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty()) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "User must have username and password defined.");
        }
        // kontrola, ci je uz pouzivatlske meno zabrate
        User existingUser = userPersistenceService.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new HttpException(HttpReturnCode.CONFLICT,
                    String.format("User with username '%s' already exists", existingUser.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userPersistenceService.persist(user);
    }

    @Transactional(rollbackFor = HttpException.class)
    public User updateUser(Integer userId, User updateUser) throws HttpException {
        log.info("updateUser: userId : {}, updateUser : {}", userId, updateUser);
        if (userId == null || updateUser == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Missing update user's info.");
        }
        User user = userPersistenceService.findById(userId);
        if (user == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "User not found by id: " + userId);
        }
        if (updateUser.getUsername() != null && !updateUser.getUsername().isEmpty()) {
            User userByUsername = userPersistenceService.findByUsername(updateUser.getUsername());
            // kontrola, ci je uz pouzivatlske meno zabrate
            if (userByUsername != null && !userByUsername.getId().equals(userId)) {
                throw new HttpException(HttpReturnCode.CONFLICT,
                        String.format("User with username '%s' already exists.", updateUser.getUsername()));
            }
            // kontrola, ci pouzivatel meni sam sebe meno
            if (!PrincipalService.getPrincipalId().equals(userId)
                    && !updateUser.getUsername().equals(user.getUsername())) {
                throw new HttpException(HttpReturnCode.CONFLICT, "Only user can change his own username.");
            }
            user.setUsername(updateUser.getUsername());
        }
        // zasifrovanie hesla, ak bolo zadane na zmenu
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        if (updateUser.getEmail() != null && !updateUser.getEmail().isEmpty()) {
            user.setEmail(updateUser.getEmail());
        }
        if (updateUser.getFullName() != null && !updateUser.getFullName().isEmpty()) {
            user.setFullName(updateUser.getFullName());
        }
        return userPersistenceService.persist(user);
    }

    public List<Role> getUsersRoles(Integer userId) throws HttpException {
        log.info("getUserRoles: {}", userId);
        User user = userId == null ? null : userPersistenceService.findById(userId);
        if (user == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "User not found by id: " + userId);
        }
        return rolePersistenceService.getRolesByUserId(userId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public List<Role> setUsersRoles(Integer userId, List<Integer> rolesIds) throws HttpException {
        log.info("setUserRoles: userId: {}, rolesIds: {}", userId, rolesIds);
        if (rolesIds == null) {
            rolesIds = new ArrayList<>();
        }
        User user = userId == null ? null : userPersistenceService.findById(userId);
        if (user == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "User not found by id: " + userId);
        }
        // ziskanie id roli pouzivatele
        List<Integer> userRolesIds = rolePersistenceService.getRolesByUserId(userId).stream().map(Role::getId).toList();
        List<Integer> allRolesIds = rolePersistenceService.findAll().stream().map(Role::getId).toList();
        // odstranenie roli, ktore nie su v novom zozname
        for (Integer id : userRolesIds) {
            if (!rolesIds.contains(id)) {
                rolePersistenceService.removeRoleFromUser(userId, id);
            }
        }
        // pridanie novych roli
        for (Integer id : rolesIds) {
            if (!allRolesIds.contains(id)) {
                throw new HttpException(HttpReturnCode.NOT_FOUND, "Role not found by id: " + id);
            }
            if (!userRolesIds.contains(id)) {
                rolePersistenceService.addRoleToUser(userId, id);
            }
        }
        return rolePersistenceService.getRolesByUserId(userId);
    }
}
