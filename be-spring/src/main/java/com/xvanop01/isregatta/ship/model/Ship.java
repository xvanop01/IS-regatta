package com.xvanop01.isregatta.ship.model;

import com.xvanop01.isregatta.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ship_ship")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String registration;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
