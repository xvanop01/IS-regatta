package com.xvanop01.isregatta.race.controller;

import com.xvanop01.isregatta.api.race.RacesApi;
import com.xvanop01.isregatta.api.race.model.CreateUpdateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.api.race.model.RaceUserInfoDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.race.mapper.RaceMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.RaceSigned;
import com.xvanop01.isregatta.race.service.RaceService;
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
public class RaceController implements RacesApi {

    private final RaceService raceService;
    private final SecurityService securityService;
    private final RaceMapper raceMapper;

    @Override
    public ResponseEntity<RaceDetailDto> createRace(CreateUpdateRaceDto createRaceDto) {
        log.info("createRace: {}", createRaceDto);
        Race race = raceMapper.map(createRaceDto);
        try {
            securityService.hasAnyRole(SecurityService.ROLE_ADMIN, SecurityService.ROLE_ORGANIZER);
            race = raceService.createRace(race);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(raceMapper.map(race));
    }

    @Override
    public ResponseEntity<RaceDetailDto> getRace(Integer raceId) {
        log.info("getRace: raceId: {}", raceId);
        Race race;
        try {
            race = raceService.getRaceById(raceId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(raceMapper.map(race));
    }

    @Override
    public ResponseEntity<RaceUserInfoDto> isSignedUp(Integer raceId) {
        log.info("isSignedUp: raceId: {}", raceId);
        RaceSigned raceSigned = raceService.isSignedUp(raceId);
        return ResponseEntity.ok(raceMapper.map(raceSigned));
    }

    @Override
    public ResponseEntity<RaceUserInfoDto> signUpActive(Integer raceId) {
        log.info("signUpActive: raceId: {}", raceId);
        RaceSigned raceSigned;
        try {
            raceSigned = raceService.signUpActive(raceId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(raceMapper.map(raceSigned));
    }

    @Override
    public ResponseEntity<RaceDetailDto> updateRace(Integer raceId, CreateUpdateRaceDto updateRaceDto) {
        log.info("updateRace: raceId: {}, updateRaceDto: {}", raceId, updateRaceDto);
        Race race = raceMapper.map(updateRaceDto);
        try {
            securityIsMainOrganizerOrAdmin(raceId);
            race = raceService.updateRace(raceId, race);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(raceMapper.map(race));
    }

    private void securityIsMainOrganizerOrAdmin(Integer raceId) throws HttpException {
        log.info("securityIsMainOrganizerOrAdmin: raceId: {}", raceId);
        Race race = raceService.getRaceById(raceId);
        if (race.getOrganizer().getId().equals(PrincipalService.getPrincipalId())) {
            return;
        }
        securityService.hasRole(SecurityService.ROLE_ADMIN);
    }
}
