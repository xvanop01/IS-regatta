package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.CrewUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewUserRepository extends JpaRepository<CrewUser, Integer>, JpaSpecificationExecutor<CrewUser> {

    Optional<CrewUser> findByUserIdAndCrewRaceId(Integer userId, Integer raceId);
}
