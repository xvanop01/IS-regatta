package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.race.model.CrewUser;
import com.xvanop01.isregatta.race.repository.CrewUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrewUserPersistnceService extends PersistenceService<CrewUser, CrewUserRepository> {

    public CrewUserPersistnceService(CrewUserRepository repository) {
        super(repository);
    }

    public CrewUser getByUserIdAndRaceId(Integer userId, Integer raceId) {
        log.info("getByUserIdAndRaceId: userId: {}, raceId: {}", userId, raceId);
        return repository.findByUserIdAndCrewRaceId(userId, raceId).orElse(null);
    }
}
