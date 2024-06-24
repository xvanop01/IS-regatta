package com.xvanop01.isregatta.ship.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.repository.ShipRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ShipPersistenceService
 * Spracovanie udajov o lodiach z DB
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
public class ShipPersistenceService extends PersistenceService<Ship, ShipRepository> {

    public ShipPersistenceService(ShipRepository repository) {
        super(repository);
    }

    public List<Ship> findAllByUserIdNotInRace(Integer userId, Integer raceId) {
        log.info("findAllByUserIdNotInRace: userId: {}, raceId: {}", userId, raceId);
        return repository.findAllByUserNotInRace(userId, raceId);
    }
}
