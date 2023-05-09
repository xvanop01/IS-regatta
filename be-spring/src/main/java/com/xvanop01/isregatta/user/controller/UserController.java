package com.xvanop01.isregatta.user.controller;

import com.xvanop01.isregatta.api.user.model.CreateUserDto;
import com.xvanop01.isregatta.api.user.model.RoleListDto;
import com.xvanop01.isregatta.api.user.model.UpdateUserDto;
import com.xvanop01.isregatta.api.user.model.UserDetailDto;
import com.xvanop01.isregatta.api.user.model.UserDetailListDto;
import com.xvanop01.isregatta.api.user.UserControllerApi;
import com.xvanop01.isregatta.base.exception.Http400ReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.user.mapper.RoleMapper;
import com.xvanop01.isregatta.user.mapper.UserMapper;
import com.xvanop01.isregatta.user.model.Role;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.RolePersistenceService;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class UserController implements UserControllerApi {

    @Autowired
    public UserService userService;

    @Autowired
    public RolePersistenceService rolePersistenceService;

    @Autowired
    public SecurityService securityService;

    @Autowired
    public HttpExceptionHandler httpExceptionHandler;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public RoleMapper roleMapper;

    @Override
    public ResponseEntity<UserDetailDto> createUser(CreateUserDto createUserDto) {
        log.info("createUser: {}", createUserDto);
        User user = userMapper.map(createUserDto);
        try {
            user = userService.createUser(user);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(201).body(userMapper.map(user));
    }

    @Override
    public ResponseEntity<UserDetailListDto> getAllUsers() {
        log.info("getAllUsers");
        try {
            if (!securityService.hasRole(SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        List<User> users = userService.getAllUsers();
        UserDetailListDto dto = new UserDetailListDto();
        dto.setUsers(userMapper.map(users));
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<RoleListDto> getRoles() {
        log.info("getRoles");
        try {
            if (!securityService.hasRole(SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        List<Role> roles = rolePersistenceService.getAllRoles();
        RoleListDto dto = roleMapper.mapRoleList(roles);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UserDetailDto> getUser(Integer userId) {
        log.info("getUser: {}", userId);
        User user;
        try {
            if (!securityService.hasRoleOrIsUser(userId, SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
            user = userService.getUserById(userId);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(userMapper.map(user));
    }

    @Override
    public ResponseEntity<RoleListDto> getUserRoles(Integer userId) {
        log.info("getUserRoles: {}", userId);
        List<Role> roles;
        try {
            if (!securityService.hasRoleOrIsUser(userId, SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
            roles = userService.getUsersRoles(userId);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        RoleListDto dto = roleMapper.mapRoleList(roles);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UserDetailDto> updateUser(Integer userId, UpdateUserDto updateUserDto) {
        log.info("updateUser: userId: {}, createUserDto: {}", userId, updateUserDto);
        User user = userMapper.map(updateUserDto);
        try {
            if (!securityService.hasRoleOrIsUser(userId, SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
            user = userService.updateUser(userId, user);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(userMapper.map(user));
    }

    @Override
    public ResponseEntity<RoleListDto> updateUserRoles(Integer userId, RoleListDto roleListDto) {
        log.info("updateUserRoles: userId: {}, roleListDto: {}", userId, roleListDto);
        List<Integer> rolesIds = roleMapper.map(roleListDto);
        List<Role> roles;
        try {
            if (!securityService.hasRole(SecurityService.ROLE_ADMIN)) {
                throw new HttpException(Http400ReturnCode.FORBIDDEN, "You don't have permission for the resource.");
            }
            roles = userService.setUsersRoles(userId, rolesIds);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        RoleListDto dto = roleMapper.mapRoleList(roles);
        return ResponseEntity.ok(dto);
    }
}
