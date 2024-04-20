package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.CrewStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CrewRepository extends JpaRepository<Crew, Integer>, JpaSpecificationExecutor<Crew> {

    Boolean existsByRaceIdAndShipIdIn(Integer raceId, List<Integer> shipIds);

    @Query(value = "SELECT crew FROM Crew crew WHERE crew.race.id = ?1")
    Page<Crew> findAllByRaceId(Integer raceId, Specification<Crew> specification, Pageable pageable);

    List<Crew> findAllByRaceIdAndStatus(Integer raceId, CrewStatus status);
}
