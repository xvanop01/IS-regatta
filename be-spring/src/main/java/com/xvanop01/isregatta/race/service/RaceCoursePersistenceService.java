package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.race.model.RaceCourse;
import com.xvanop01.isregatta.race.repository.RaceCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * RaceCoursePersistenceService
 * Spracovanie udajov o trati pretekov z DB
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
public class RaceCoursePersistenceService extends PersistenceService<RaceCourse, RaceCourseRepository> {

    public RaceCoursePersistenceService(RaceCourseRepository repository) {
        super(repository);
    }
}
