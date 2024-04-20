package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.CrewStatus;
import com.xvanop01.isregatta.race.repository.CrewRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CrewPersistenceService extends PersistenceService<Crew, CrewRepository> {

    public CrewPersistenceService(CrewRepository repository) {
        super(repository);
    }

    public Boolean existsByRaceIdAndShipIdIn(Integer raceId, List<Integer> shipsIds) {
        log.info("existsByRaceIdAndShipIdIn: raceId: {}, shipsIds: {}", raceId, shipsIds);
        return repository.existsByRaceIdAndShipIdIn(raceId, shipsIds);
    }

    @Transactional
    public List<Crew> persistAll(List<Crew> crews) {
        log.info("persistAll: crews: {}", crews);
        return repository.saveAll(crews);
    }

    @Transactional
    public List<Crew> acceptAllByRaceId(Integer raceId) {
        log.info("acceptAllByRaceId: raceId: {}", raceId);
        List<Crew> crews = repository.findAllByRaceIdAndStatus(raceId, CrewStatus.APPLIED);
        if (crews == null || crews.isEmpty()) {
            return null;
        }
        crews.forEach(crew -> crew.setStatus(CrewStatus.REGISTERED));
        return repository.saveAll(crews);
    }
}
