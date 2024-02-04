package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.templates.PersistenceService;
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

    public Race getRaceByIdAndOrganizerId(Integer raceId, Integer organizerId) {
        log.info("getRaceByIdAndOrganizerId: raceId: {}, organizerId: {}", raceId, organizerId);
        return repository.findByIdAndOrganizerId(raceId, organizerId).orElse(null);
    }
}
