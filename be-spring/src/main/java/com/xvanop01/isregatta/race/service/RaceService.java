package com.xvanop01.isregatta.race.service;

import com.xvanop01.isregatta.base.exception.HttpReturnCode;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.CrewUser;
import com.xvanop01.isregatta.race.model.RaceCourse;
import com.xvanop01.isregatta.race.model.RegistrationStatus;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.service.ShipPersistenceService;
import com.xvanop01.isregatta.user.model.User;
import com.xvanop01.isregatta.user.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RaceService
 * Servis zabezpecujuci aplikacnu logiku pre preteky
 * @author 2024 Peter Vano
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RaceService {

    private final RacePersistenceService racePersistenceService;
    private final CrewPersistenceService crewPersistenceService;
    private final CrewUserPersistnceService crewUserPersistnceService;
    private final ShipPersistenceService shipPersistenceService;
    private final RaceCoursePersistenceService raceCoursePersistenceService;
    private final SecurityService securityService;
    private final UserService userService;

    @Transactional(rollbackFor = HttpException.class)
    public Race createRace(Race race) throws HttpException {
        log.info("createRace: {}", race);
        if (race == null || race.getName() == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Race is badly defined.");
        }
        if (race.getIsPublic() == null || !race.getIsPublic()) {
            race.setIsPublic(false);
        }
        if (race.getSignUpUntil() != null && race.getDate() != null && race.getSignUpUntil().isAfter(race.getDate())) {
            throw new HttpException(HttpReturnCode.CONFLICT, "Race date is before registration closing.");
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
        if (updateRace.getSignUpUntil() != null && updateRace.getDate() != null
                && updateRace.getSignUpUntil().isAfter(updateRace.getDate())) {
            throw new HttpException(HttpReturnCode.CONFLICT, "Race date is before registration closing.");
        }
        if (!race.getIsPublic() && updateRace.getIsPublic() != null && updateRace.getIsPublic()) {
            crewPersistenceService.acceptAllByRaceId(raceId);
        }
        race.setName(updateRace.getName());
        race.setLocation(updateRace.getLocation());
        race.setDate(updateRace.getDate());
        race.setSignUpUntil(updateRace.getSignUpUntil());
        race.setDescription(updateRace.getDescription());
        race.setIsPublic(updateRace.getIsPublic() != null && updateRace.getIsPublic());
        return racePersistenceService.persist(race);
    }

    /**
     * Ziska lode, ktore moze aktualny pouzvatel registrovat na preteky
     * @param raceId id pretekov
     * @return zoznam lodi
     * @throws HttpException chybajuce udaje
     */
    @Transactional(rollbackFor = HttpException.class)
    public List<Ship> getShipsForRace(Integer raceId) throws HttpException {
        log.info("getShipsForRace: raceId: {}", raceId);
        Integer userId = PrincipalService.getPrincipalId();
        if (userId == null) {
            throw new HttpException(HttpReturnCode.UNAUTHORIZED, "User is not authenticated.");
        }
        if (raceId == null) {
            throw new HttpException(HttpReturnCode.BAD_REQUEST, "Missing raceId.");
        }
        return shipPersistenceService.findAllByUserIdNotInRace(userId, raceId);
    }

    /**
     * Prihlasi zoznam lodi na preteky vytvorenim posadok
     * @param raceId id pretekov
     * @param shipsIds id lodi
     * @return vytvorene posadky
     * @throws HttpException posadky nevytvorene
     */
    @Transactional(rollbackFor = HttpException.class)
    public List<Crew> signUpShipsForRace(Integer raceId, List<Integer> shipsIds) throws HttpException {
        log.info("signUpShipsForRace: raceId: {}, shipIds: {}", raceId, shipsIds);
        // overovanie
        if (crewPersistenceService.existsByRaceIdAndShipIdIn(raceId, shipsIds)) {
            throw new HttpException(HttpReturnCode.CONFLICT, "One of the ships already registered.");
        }
        Integer userId = PrincipalService.getPrincipalId();
        Boolean isAdmin = securityService.isAdmin();
        if (userId == null) {
            throw new HttpException(HttpReturnCode.UNAUTHORIZED, "User is not authenticated.");
        }
        Race race = racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        if (race.getSignUpUntil() == null || race.getSignUpUntil().isBefore(LocalDate.now())) {
            throw new HttpException(HttpReturnCode.CONFLICT, "Registration is closed.");
        }
        // vytvranie posadok
        RegistrationStatus status = race.getIsPublic() ? RegistrationStatus.REGISTERED : RegistrationStatus.APPLIED;
        List<Crew> crews = new ArrayList<>();
        for (Integer shipId : shipsIds) {
            Ship ship = shipPersistenceService.findById(shipId);
            if (ship == null) {
                throw new HttpException(HttpReturnCode.NOT_FOUND, "Ship not found by id: " + shipId);
            }
            if (!Objects.equals(ship.getOwner().getId(), userId) && !isAdmin) {
                throw new HttpException(HttpReturnCode.CONFLICT,
                        "You do not have permission to register a ship with id: " + shipId);
            }
            Crew crew = new Crew();
            crew.setShip(ship);
            crew.setRace(race);
            crew.setStatus(status);
            crews.add(crew);
        }
        return crewPersistenceService.persistAll(crews);
    }

    public Crew getCrewById(Integer crewId) throws HttpException {
        log.info("getCrewById: crewId: {}", crewId);
        Crew crew = crewPersistenceService.findById(crewId);
        if (crew == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew not found by id: " + crewId);
        }
        return crew;
    }

    /**
     * Schvali registraciu posadky do pretekov
     * @param crewId id posadky
     * @return detail posadky
     * @throws HttpException nenajdeny, nema opravnenie
     */
    @Transactional(rollbackFor = HttpException.class)
    public Crew acceptCrew(Integer crewId) throws HttpException {
        log.info("acceptCrew: crewId: {}", crewId);
        Crew crew = crewPersistenceService.findById(crewId);
        if (crew == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew not found by id: " + crewId);
        }
        if (!Objects.equals(crew.getRace().getOrganizer().getId(), PrincipalService.getPrincipalId())
                && !securityService.isAdmin()) { // iba organizator danych pretekov a admin mozu
            throw new HttpException(HttpReturnCode.FORBIDDEN, "You do not have permission to accept a crew.");
        }
        crew.setStatus(RegistrationStatus.REGISTERED);
        return crewPersistenceService.persist(crew);
    }

    /**
     * Zamietne registraciu do pretekov a zrusi posadku
     * @param crewId id posadky
     * @return detail posadky
     * @throws HttpException nenajdeny, nema opravnenie
     */
    @Transactional(rollbackFor = HttpException.class)
    public void removeCrew(Integer crewId) throws HttpException {
        log.info("removeCrew: crewId: {}", crewId);
        Crew crew = crewPersistenceService.findById(crewId);
        if (crew == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND,
                    "Crew not found by id: " + crewId);
        }
        if (!Objects.equals(crew.getRace().getOrganizer().getId(), PrincipalService.getPrincipalId())
                && !securityService.isAdmin()) { // iba organizator danych pretekov a admin mozu
            throw new HttpException(HttpReturnCode.FORBIDDEN, "You do not have permission to remove a crew.");
        }
        crewPersistenceService.removeById(crewId);
    }

    /**
     * Ziska posadku pre aktivne pouzivatela, ak je clenom nejakej na danych pretekoch
     * @param raceId id pretekov
     * @return detail registracie
     */
    public CrewUser getCrewForActive(Integer raceId) {
        log.info("getCrewForActive: raceId: {}", raceId);
        Integer userId = PrincipalService.getPrincipalId();
        return crewUserPersistnceService.getByUserIdAndRaceId(userId, raceId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public CrewUser applyToCrew(Integer crewId) throws HttpException {
        log.info("applyToCrew: crewId: {}", crewId);
        Crew crew = crewPersistenceService.findById(crewId);
        if (crew == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew not found by id: " + crewId);
        }
        User user = userService.getUserById(PrincipalService.getPrincipalId());
        CrewUser crewUser = new CrewUser();
        crewUser.setCrew(crew);
        crewUser.setUser(user);
        crewUser.setStatus(RegistrationStatus.APPLIED);
        return crewUserPersistnceService.persist(crewUser);
    }

    @Transactional(rollbackFor = HttpException.class)
    public void leaveCrew(Integer crewId) throws HttpException {
        log.info("leaveCrew: crewId: {}", crewId);
        CrewUser crewUser = crewUserPersistnceService.getByCrewIdAndUserId(crewId, PrincipalService.getPrincipalId());
        if (crewUser == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew user not found by crewId: " + crewId);
        }
        crewUserPersistnceService.removeById(crewUser.getId());
    }

    /**
     * Schvalenie registracie pouzivatela do posadky
     * @param crewUserId id registracie
     * @return detail registracie
     * @throws HttpException nenajdeny, nema opravnenie
     */
    @Transactional(rollbackFor = HttpException.class)
    public CrewUser acceptUserToCrew(Integer crewUserId) throws HttpException {
        log.info("acceptUserToCrew: crewUserId: {}", crewUserId);
        CrewUser crewUser = crewUserPersistnceService.findById(crewUserId);
        if (crewUser == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew user not found by id: " + crewUserId);
        }
        if (!Objects.equals(crewUser.getCrew().getShip().getOwner().getId(), PrincipalService.getPrincipalId())
                && !securityService.isAdmin()) { // iba majitel danej lode a admin mozu
            throw new HttpException(HttpReturnCode.FORBIDDEN, "You do not have permission to accept a user.");
        }
        crewUser.setStatus(RegistrationStatus.REGISTERED);
        return crewUserPersistnceService.persist(crewUser);
    }

    /**
     * Zrusi alebo zamietne registraciu pouzivatela do posadky
     * @param crewUserId id registracie
     * @throws HttpException nenajdeny, nema opravnenie
     */
    @Transactional(rollbackFor = HttpException.class)
    public void removeUserFromCrew(Integer crewUserId) throws HttpException {
        log.info("removeUserFromCrew: crewUserId: {}", crewUserId);
        CrewUser crewUser = crewUserPersistnceService.findById(crewUserId);
        if (crewUser == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew user not found by id: " + crewUserId);
        }
        if (!Objects.equals(crewUser.getCrew().getShip().getOwner().getId(), PrincipalService.getPrincipalId())
                && !securityService.isAdmin()) { // iba majitel danej lode a admin mozu
            throw new HttpException(HttpReturnCode.FORBIDDEN, "You do not have permission to remove a user.");
        }
        crewUserPersistnceService.removeById(crewUser.getId());
    }

    @Transactional(rollbackFor = HttpException.class)
    public Crew updateResults(Integer crewId, Crew results) throws HttpException {
        log.info("updateResults: crewId: {}, results: {}", crewId, results);
        Crew crew = crewPersistenceService.findById(crewId);
        if (crew == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Crew not found by id: " + crewId);
        }
        if (results.getPosition() != null && results.getPosition() <= 0) {
            throw new HttpException(HttpReturnCode.CONFLICT, "Position must be positive number.");
        }
        crew.setPosition(results.getPosition());
        crew.setFinishingTime(results.getFinishingTime());
        return crewPersistenceService.persist(crew);
    }

    public RaceCourse getRaceCourse(Integer raceId) {
        log.info("getRaceCourse: raceId: {}", raceId);
        return raceCoursePersistenceService.findById(raceId);
    }

    @Transactional(rollbackFor = HttpException.class)
    public RaceCourse createUpdateRaceCourse(Integer raceId, RaceCourse raceCourse) throws HttpException {
        log.info("createUpdateRaceCourse: raceId: {}, raceCourse: {}", raceId, raceCourse);
        Race race = racePersistenceService.findById(raceId);
        if (race == null) {
            throw new HttpException(HttpReturnCode.NOT_FOUND, "Race not found by id: " + raceId);
        }
        if (!Objects.equals(race.getOrganizer().getId(), PrincipalService.getPrincipalId())
                && !securityService.isAdmin()) { // iba organizator danych pretekov a admin mozu
            throw new HttpException(HttpReturnCode.FORBIDDEN, "You do not have permission to update a race's course.");
        }
        raceCourse.setRaceId(raceId);
        return raceCoursePersistenceService.persist(raceCourse);
    }
}
