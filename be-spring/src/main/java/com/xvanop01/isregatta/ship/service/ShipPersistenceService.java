package com.xvanop01.isregatta.ship.service;

import com.xvanop01.isregatta.base.support.template.PersistenceService;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.repository.ShipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipPersistenceService extends PersistenceService<Ship, ShipRepository> {

    public ShipPersistenceService(ShipRepository repository) {
        super(repository);
    }
}
