package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/**
 * RaceRepository
 * Repozitar na ziskavanie informacii o pretekoch z DB
 * @author 2024 Peter Vano
 */
public interface RaceRepository extends JpaRepository<Race, Integer>, JpaSpecificationExecutor<Race> {

    Optional<Race> findByIdAndOrganizerId(Integer raceId, Integer organizerId);

    /**
     * Ziska zoznam pretekov, kde bol pouzivatel clenom niektorej posadky
     * @param userId id pouzivatela
     * @param specification specifikacia filtrovania
     * @param pageable specifikacia strany
     * @return stranu pozadovanych zaznamov
     */
    @Query(value = "SELECT race.* "
            + "FROM race_race race "
            + "  RIGHT JOIN race_crew crew ON race.id = crew.race_id "
            + "  RIGHT JOIN race_crew_user crew_user ON crew.id = crew_user.crew_id "
            + "WHERE crew_user.user_id = ?1 "
            + "ORDER BY race.id", nativeQuery = true)
    Page<Race> findByUserId(Integer userId, Specification<Race> specification, Pageable pageable);
}
