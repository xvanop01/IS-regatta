package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.RaceSigned;
import com.xvanop01.isregatta.race.model.RaceSignedStatus;
import com.xvanop01.isregatta.race.repository.RaceSignedRepository;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaceService {

    private final RacePersistenceService racePersistenceService;
    private final UserService userService;
    private final RaceSignedRepository raceSignedRepository;

    @Transactional(rollbackFor = HttpException.class)
    public Race createRace(Race race) throws HttpException {
        log.info("createRace: {}", race);
        if (race == null || race.getName() == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Race is badly defined.");
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
        throw new HttpException(HttpReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public Race updateRace(Integer raceId, Race updateRace) throws HttpException {
        log.info("updateRace: raceId: {}, updateRace: {}", raceId, updateRace);
        if (raceId == null || updateRace == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Missing update race's info.");
        }
        Race race = racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
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
        if (updateRace.getIsPublic() != null) {
            race.setIsPublic(updateRace.getIsPublic());
        }
        return racePersistenceService.persist(race);
    }

    @Transactional(rollbackFor = HttpException.class)
    public RaceSigned signUpActive(Integer raceId) throws HttpException {
        log.info("signUpActive: {}", raceId);
        User user = userService.getUserById(PrincipalService.getPrincipalId());
        if (user == null) {
            throw new HttpException(HttpReturnCode.UNAUTHORIZED, "User not logged in.");
        }
        Race race = racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        RaceSigned raceSigned = new RaceSigned();
        raceSigned.setUser(user);
        raceSigned.setRace(race);
        if (race.getIsPublic() == null || !race.getIsPublic()) {
            raceSigned.setStatus(RaceSignedStatus.APPLIED);
        } else {
            raceSigned.setStatus(RaceSignedStatus.SIGNED_UP);
        }
        raceSigned = raceSignedRepository.save(raceSigned);
        return raceSigned;
    }

    public RaceSigned isSignedUp(Integer raceId) {
        log.info("isSignedUp: {}", raceId);
        Integer userId = PrincipalService.getPrincipalId();
        return raceSignedRepository.findByRaceIdAndUserId(raceId, userId).orElse(null);
    }
}
