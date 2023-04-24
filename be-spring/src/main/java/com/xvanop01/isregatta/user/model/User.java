package com.xvanop01.isregatta.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;
}
