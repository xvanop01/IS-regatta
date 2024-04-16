package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.RaceSigned;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RaceSignedRepository extends JpaRepository<RaceSigned, Integer>, JpaSpecificationExecutor<RaceSigned> {

    Optional<RaceSigned> findByRaceIdAndUserId(Integer raceId, Integer userId);
}
