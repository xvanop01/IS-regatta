package com.xvanop01.isregatta.user.repository;

import com.xvanop01.isregatta.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * UserRepository
 * Repozitar pre pracu s pouzivatelmi v DB
 * @author 2024 Peter Vano
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
}
