package com.xvanop01.isregatta.ship.controller;

import com.xvanop01.isregatta.api.ship.ShipsApi;
import com.xvanop01.isregatta.api.ship.model.CreateUpdateShipDto;
import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.ship.mapper.ShipMapper;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.service.ShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShipController implements ShipsApi {

    private final ShipService shipService;
    private final SecurityService securityService;
    private final ShipMapper shipMapper;

    @Override
    public ResponseEntity<ShipDetailDto> createShip(CreateUpdateShipDto createUpdateShipDto) {
        log.info("createShip: {}", createUpdateShipDto);
        Ship ship = shipMapper.map(createUpdateShipDto);
        try {
            ship = shipService.createShip(ship);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(shipMapper.map(ship));
    }

    @Override
    public ResponseEntity<ShipDetailDto> getShip(Integer shipId) {
        log.info("getShip: shipId: {}", shipId);
        Ship ship;
        try {
            ship = shipService.getShipById(shipId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(shipMapper.map(ship));
    }

    @Override
    public ResponseEntity<ShipDetailDto> updateShip(Integer shipId, CreateUpdateShipDto createUpdateShipDto) {
        log.info("updateShip: shipId: {}, dto: {}", shipId, createUpdateShipDto);
        Ship ship = shipMapper.map(createUpdateShipDto);
        try {
            securityIsOwnerOrAdmin(shipId);
            ship = shipService.updateShip(shipId, ship);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(shipMapper.map(ship));
    }

    private void securityIsOwnerOrAdmin(Integer shipId) throws HttpException {
        log.info("securityIsOwnerOrAdmin: shipId: {}", shipId);
        Ship ship = shipService.getShipById(shipId);
        if (ship.getOwner().getId().equals(PrincipalService.getPrincipalId())) {
            return;
        }
        securityService.hasRole(SecurityService.ROLE_ADMIN);
    }
}
