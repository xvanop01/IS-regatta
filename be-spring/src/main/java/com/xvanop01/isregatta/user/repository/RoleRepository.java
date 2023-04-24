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
            "WHERE ur.role_id = ?1 ", nativeQuery = true)
    List<Role> findAllByUserId(Integer userId);
}
