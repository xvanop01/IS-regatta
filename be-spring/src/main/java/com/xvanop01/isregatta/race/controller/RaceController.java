package com.xvanop01.isregatta.race.controller;

import com.xvanop01.isregatta.api.race.RaceControllerApi;
import com.xvanop01.isregatta.api.race.model.CreateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailListDto;
import com.xvanop01.isregatta.api.race.model.UpdateRaceDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.race.mapper.RaceMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.service.RaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class RaceController implements RaceControllerApi {

    @Autowired
    private RaceService raceService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HttpExceptionHandler httpExceptionHandler;

    @Autowired
    private RaceMapper raceMapper;

    @Override
    public ResponseEntity<RaceDetailDto> activateRace(Integer raceId) {
        log.info("activateRace: {}", raceId);
        Race race;
        try {
            securityService.hasRole(SecurityService.ROLE_ORGANIZER);
            race = raceService.openRaceForSignUp(raceId);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(raceMapper.map(race));
    }

    @Override
    public ResponseEntity<RaceDetailDto> createRace(CreateRaceDto createRaceDto) {
        log.info("createRace: {}", createRaceDto);
        Race race = raceMapper.map(createRaceDto);
        try {
            securityService.hasRole(SecurityService.ROLE_ORGANIZER);
            race = raceService.createRace(race);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(201).body(raceMapper.map(race));
    }

    @Override
    public ResponseEntity<RaceDetailListDto> getAllRaces() {
        log.info("getAllRaces");
        List<Race> raceList = raceService.getAllRaces();
        return ResponseEntity.ok(raceMapper.mapList(raceList));
    }

    @Override
    public ResponseEntity<RaceDetailDto> getRace(Integer raceId) {
        log.info("getRace");
        Race race;
        try {
            race = raceService.getRaceById(raceId);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(raceMapper.map(race));
    }

    @Override
    public ResponseEntity<RaceDetailDto> updateRace(Integer raceId, UpdateRaceDto updateRaceDto) {
        log.info("updateRace: raceId: {}, updateRaceDto: {}", raceId, updateRaceDto);
        Race race = raceMapper.map(updateRaceDto);
        try {
            securityService.hasRole(SecurityService.ROLE_ORGANIZER);
            race = raceService.updateRace(raceId, race);
        } catch (HttpException e) {
            return httpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(raceMapper.map(race));
    }
}
