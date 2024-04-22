package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.RegistrationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewRepository extends JpaRepository<Crew, Integer>, JpaSpecificationExecutor<Crew> {

    Boolean existsByRaceIdAndShipIdIn(Integer raceId, List<Integer> shipIds);

    List<Crew> findAllByRaceIdAndStatus(Integer raceId, RegistrationStatus status);
}
