package com.xvanop01.isregatta.ship.repository;

import com.xvanop01.isregatta.ship.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShipRepository extends JpaRepository<Ship, Integer>, JpaSpecificationExecutor<Ship> {

}
