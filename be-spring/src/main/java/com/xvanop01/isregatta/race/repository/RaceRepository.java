package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RaceRepository extends JpaRepository<Race, Integer>, JpaSpecificationExecutor<Race> {

}
