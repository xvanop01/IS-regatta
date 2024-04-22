package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.repository.RaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RacePersistenceService extends PersistenceService<Race, RaceRepository> {

    public RacePersistenceService(RaceRepository repository) {
        super(repository);
    }
}
