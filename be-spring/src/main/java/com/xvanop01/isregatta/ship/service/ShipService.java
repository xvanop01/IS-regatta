package com.xvanop01.isregatta.ship.service;

import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipService {

    private final ShipPersistenceService shipPersistenceService;
    private final UserService userService;

    public Ship getShipById(Integer shipId) throws HttpException {
        log.info("getShipById: {}", shipId);
        if (shipId != null) {
            Ship ship = shipPersistenceService.findById(shipId);
            if (ship != null) {
                return ship;
            }
        }
        throw new HttpException(HttpReturnCode.NOT_FOUND, "Ship not found by id: " + shipId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public Ship createShip(Ship ship) throws HttpException {
        log.info("createShip: {}", ship);
        User user = userService.getUserById(PrincipalService.getPrincipalId());
        ship.setOwner(user);
        return shipPersistenceService.persist(ship);
    }

    @Transactional(rollbackFor = HttpException.class)
    public Ship updateShip(Integer shipId, Ship updateShip) throws HttpException {
        log.info("updateShip: shipId: {}, updateShip: {}", shipId, updateShip);
        if (shipId == null || updateShip == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Missing update ship's info.");
        }
        Ship ship = shipPersistenceService.findById(shipId);
        if (ship == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Ship not found by id: " + shipId);
        }
        ship.setName(updateShip.getName());
        ship.setRegistration(updateShip.getRegistration());
        return shipPersistenceService.persist(ship);
    }
}
