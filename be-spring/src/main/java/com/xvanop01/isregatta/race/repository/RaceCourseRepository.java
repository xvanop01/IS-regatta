package com.xvanop01.isregatta.race.repository;

import com.xvanop01.isregatta.race.model.RaceCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RaceCourseRepository extends JpaRepository<RaceCourse, Integer>, JpaSpecificationExecutor<RaceCourse> {

}
