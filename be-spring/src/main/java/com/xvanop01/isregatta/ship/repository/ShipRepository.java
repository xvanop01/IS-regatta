package com.xvanop01.isregatta.ship.repository;

import com.xvanop01.isregatta.ship.model.Ship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ShipRepository extends JpaRepository<Ship, Integer>, JpaSpecificationExecutor<Ship> {

    @Query(value = "SELECT * "
            + "FROM ship_ship ship "
            + "WHERE ship.owner_id = ?1 "
            + "  AND NOT EXISTS("
            + "    SELECT * "
            + "    FROM race_crew crew "
            + "    WHERE ship.id = crew.ship_id "
            + "      AND race_id = 1 "
            + "  )", nativeQuery = true)
    List<Ship> findAllByUserNotInRace(Integer userId, Integer raceId);
}
