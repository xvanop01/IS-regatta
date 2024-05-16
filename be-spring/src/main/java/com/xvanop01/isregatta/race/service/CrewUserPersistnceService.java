package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.race.model.CrewUser;
import com.xvanop01.isregatta.race.repository.CrewUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * CrewUserPersistnceService
 * Spracovanie udajov registracii pouzivatela z DB
 * @author 2024 Peter Vano
 */
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

    public CrewUser getByCrewIdAndUserId(Integer crewId, Integer userId) {
        log.info("getByCrewIdAndUserId: crewId: {}, userId: {}", crewId, userId);
        return repository.findByCrewIdAndUserId(crewId, userId).orElse(null);
    }
}
