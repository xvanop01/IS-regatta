package com.xvanop01.isregatta.user.repository;

import com.xvanop01.isregatta.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    @Query(value = "SELECT \n" +
            " r.id, \n" +
            " r.role \n" +
            "FROM user_user_role ur \n" +
            " LEFT JOIN user_role r ON ur.role_id = r.id \n" +
            "WHERE ur.user_id = ?1 ", nativeQuery = true)
    List<Role> findAllByUserId(Integer userId);

    @Query(value = "SELECT \n" +
            " r.id, \n" +
            " r.role \n" +
            "FROM user_user u \n" +
            " LEFT JOIN user_user_role ur ON u.id = ur.user_id \n" +
            " LEFT JOIN user_role r ON ur.role_id = r.id \n" +
            "WHERE u.username = ?1 ", nativeQuery = true)
    List<Role> findAllByUsername(String username);

    @Query(value = "INSERT INTO user_user_role (`user_id`, `role_id`) VALUES(?1, ?2) ", nativeQuery = true)
    void addRoleToUser(Integer userId, Integer roleId);

    @Query(value = "DELETE FROM user_user_role \n" +
            "WHERE user_id = ?1 AND role_id = ?2 ", nativeQuery = true)
    void removeRoleFromUser(Integer userId, Integer roleId);
}
