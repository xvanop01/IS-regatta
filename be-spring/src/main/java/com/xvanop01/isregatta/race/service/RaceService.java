package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.exception.Http400ReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class RaceService {

    @Autowired
    private RacePersistenceService racePersistenceService;

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = HttpException.class)
    public Race createRace(Race race) throws HttpException {
        log.info("createRace: {}", race);
        if (race == null || race.getName() == null) {
            throw new HttpException(Http400ReturnCode.BAD_REQUEST, "Race is badly defined.");
        }
        if (race.getIsPublic() == null || !race.getIsPublic()) {
            race.setIsPublic(false);
        }
        User user = userService.getUserById(PrincipalService.getPrincipalId());
        race.setOrganizer(user);
        return racePersistenceService.persist(race);
    }

    public Race getRaceById(Integer raceId) throws HttpException {
        log.info("getRaceById: {}", raceId);
        if (raceId != null) {
            Race race = racePersistenceService.findById(raceId);
            if (race != null) {
                return race;
            }
        }
        throw new HttpException(Http400ReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
    }

    public List<Race> getAllRaces() {
        log.info("getAllRaces");
        return racePersistenceService.findAll();
    }

    @Transactional(rollbackFor = HttpException.class)
    public Race updateRace(Integer raceId, Race updateRace) throws HttpException {
        log.info("updateRace: raceId: {}, updateRace: {}", raceId, updateRace);
        if (raceId == null || updateRace == null) {
            throw new HttpException(Http400ReturnCode.BAD_REQUEST, "Missing update race's info.");
        }
        Race race = racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(Http400ReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        if (race.getIsPublic()) {
            throw new HttpException(Http400ReturnCode.CONFLICT, "Race with id " + raceId + " can't be updated.");
        }
        if (updateRace.getName() != null && !updateRace.getName().isEmpty()) {
            race.setName(updateRace.getName());
        }
        if (updateRace.getLocation() != null && !updateRace.getLocation().isEmpty()) {
            race.setLocation(updateRace.getLocation());
        }
        if (updateRace.getDate() != null) {
            race.setDate(updateRace.getDate());
        }
        if (updateRace.getSignUpUntil() != null) {
            race.setSignUpUntil(updateRace.getSignUpUntil());
        }
        if (updateRace.getDescription() != null && !updateRace.getDescription().isEmpty()) {
            race.setDescription(updateRace.getDescription());
        }
        return racePersistenceService.persist(race);
    }

    @Transactional(rollbackFor = HttpException.class)
    public Race openRaceForSignUp(Integer raceId) throws HttpException {
        log.info("openRaceForSignUp: {}", raceId);
        Race race = raceId == null ? null : racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(Http400ReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        if (race.getIsPublic()) {
            throw new HttpException(Http400ReturnCode.CONFLICT, "Race was already opened.");
        }
        race.setIsPublic(true);
        return racePersistenceService.persist(race);
    }

    @Transactional(rollbackFor = HttpException.class)
    public Race changeDates(Integer raceId, LocalDate signUpUntil, LocalDate raceDate) throws HttpException {
        log.info("changeDates: raceId: {}, signUpUntil: {}, raceDate: {}", raceId, signUpUntil, raceDate);
        Race race = raceId == null ? null : racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(Http400ReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        if (!race.getIsPublic()) {
            throw new HttpException(Http400ReturnCode.CONFLICT, "Race wasn't opened for registration.");
        }
        if (signUpUntil != null) {
            race.setSignUpUntil(signUpUntil);
        }
        if (raceDate != null) {
            race.setDate(raceDate);
        }
        return racePersistenceService.persist(race);
    }

    public Boolean isMainOrganizer(Integer raceId, Integer organizerId) {
        log.info("isMainOrganizer: raceId: {}, organizerId: {}", raceId, organizerId);
        Race race = racePersistenceService.getRaceByIdAndOrganizerId(raceId, organizerId);
        return race != null;
    }
}
