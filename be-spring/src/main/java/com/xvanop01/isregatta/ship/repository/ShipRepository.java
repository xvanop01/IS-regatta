package com.xvanop01.isregatta.ship.repository;

import com.xvanop01.isregatta.ship.model.Ship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * ShipRepository
 * Repozitar pre pracu s lodami v DB
 * @author 2024 Peter Vano
 */
public interface ShipRepository extends JpaRepository<Ship, Integer>, JpaSpecificationExecutor<Ship> {

    /**
     * Ziska vsetky lode, ktorych je pouzivatel majitelom a nie su prihlasene na preteky
     * @param userId id pouzivatela
     * @param raceId id pretekov
     * @return zoznam neprihlasenych lodi
     */
    @Query(value = "SELECT * "
            + "FROM ship_ship ship "
            + "WHERE ship.owner_id = ?1 "
            + "  AND NOT EXISTS("
            + "    SELECT * "
            + "    FROM race_crew crew "
            + "    WHERE ship.id = crew.ship_id "
            + "      AND race_id = ?2 "
            + "  )", nativeQuery = true)
    List<Ship> findAllByUserNotInRace(Integer userId, Integer raceId);
}
