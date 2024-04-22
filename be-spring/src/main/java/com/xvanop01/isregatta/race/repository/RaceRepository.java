package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface RaceRepository extends JpaRepository<Race, Integer>, JpaSpecificationExecutor<Race> {

    Optional<Race> findByIdAndOrganizerId(Integer raceId, Integer organizerId);

    @Query(value = "SELECT race.* "
            + "FROM race_race race "
            + "  RIGHT JOIN race_crew crew ON race.id = crew.race_id "
            + "  RIGHT JOIN race_crew_user crew_user ON crew.id = crew_user.crew_id "
            + "WHERE crew_user.user_id = ?1 "
            + "ORDER BY race.id", nativeQuery = true)
    Page<Race> findByUserId(Integer userId, Specification<Race> specification, Pageable pageable);
}
