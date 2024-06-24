package com.xvanop01.isregatta.race.controller;

import com.xvanop01.isregatta.api.race.RacesApi;
import com.xvanop01.isregatta.api.race.model.CourseDetailDto;
import com.xvanop01.isregatta.api.race.model.CreateUpdateCourseDto;
import com.xvanop01.isregatta.api.race.model.CreateUpdateRaceDto;
import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.api.race.model.CrewDetailListDto;
import com.xvanop01.isregatta.api.race.model.CrewResultsDetailDto;
import com.xvanop01.isregatta.api.race.model.CrewResultsUpdateDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.api.race.model.ShipSignUpListDto;
import com.xvanop01.isregatta.api.race.model.UserRaceInfoDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.exception.HttpExceptionHandler;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.race.mapper.RaceMapper;
import com.xvanop01.isregatta.race.model.CrewUser;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.RaceCourse;
import com.xvanop01.isregatta.race.service.RaceService;
import com.xvanop01.isregatta.race.mapper.CrewMapper;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.ship.model.Ship;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RaceController
 * Zabezpecuje funkcionalitu pretekov
 * @author 2024 Peter Vano
 */
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class RaceController implements RacesApi {

    private final RaceService raceService;
    private final SecurityService securityService;
    private final RaceMapper raceMapper;
    private final CrewMapper crewMapper;

    /**
     * Schvali registraciu posadku do preteku
     * @param crewId id posadky na schvalenie
     * @return DTO schvalenej posadky
     */
    @Override
    public ResponseEntity<CrewDetailDto> acceptCrewToRace(Integer crewId) {
        log.info("acceptCrewToRace: crewId: {}", crewId);
        Crew crew;
        try {
            crew = raceService.acceptCrew(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewMapper.map(crew));
    }

    /**
     * Schali registraciu pouziatela do posadky
     * @param crewUserId id registracie uzivatela do posadky
     * @return DTO schvaleneho pouzivatela
     */
    @Override
    public ResponseEntity<UserRaceInfoDto> acceptUserToCrew(Integer crewUserId) {
        log.info("acceptUserToCrew: crewUserId: {}", crewUserId);
        CrewUser crewUser;
        try {
            crewUser = raceService.acceptUserToCrew(crewUserId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewMapper.map(crewUser));
    }

    /**
     * Podanie ziadosti aktualne prihlaseneo pouzivatela do posadky
     * @param crewId id posadky
     * @return DTO registracie pouzivatela
     */
    @Override
    public ResponseEntity<UserRaceInfoDto> applyToCrew(Integer crewId) {
        log.info("applyToCrew: crewId: {}", crewId);
        CrewUser crewUser;
        try {
            crewUser = raceService.applyToCrew(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(crewMapper.map(crewUser));
    }

    /**
     * Vytvori nove preteky
     * @param createRaceDto udaje o pretekoch (required)
     * @return DTO vytvorenych pretekov
     */
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

    /**
     * Vytvori alebo upravy zaznam trate pretekov
     * @param raceId id pretekov (required)
     * @param createUpdateCourseDto informacie o trati (required)
     * @return DTO spracovanych informacii
     */
    @Override
    public ResponseEntity<CourseDetailDto> createUpdateRacesCourse(Integer raceId,
            CreateUpdateCourseDto createUpdateCourseDto) {
        log.info("createUpdateRacesCourse: raceId: {}, createUpdateCourseDto: {}", raceId, createUpdateCourseDto);
        boolean isNew;
        RaceCourse raceCourse = raceMapper.map(createUpdateCourseDto);
        try {
            isNew = raceService.getRaceCourse(raceId) == null;
            raceCourse = raceService.createUpdateRaceCourse(raceId, raceCourse);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(isNew ? HttpStatus.CREATED : HttpStatus.OK).body(raceMapper.map(raceCourse));
    }

    /**
     * Ziska informacie o registracii aktualne prihlaseneho pouzivatela ak je registrovany na preteky
     * @param raceId id pretekov (required)
     * @return informacie o registracii
     */
    @Override
    public ResponseEntity<UserRaceInfoDto> getActiveUserRaceInfo(Integer raceId) {
        log.info("getActiveUserRaceInfo: raceId: {}", raceId);
        CrewUser crewUser = raceService.getCrewForActive(raceId);
        return ResponseEntity.ok(crewMapper.map(crewUser));
    }

    /**
     * Ziska informacie o posadke
     * @param crewId id posadky (required)
     * @return DTO posadky
     */
    @Override
    public ResponseEntity<CrewDetailDto> getCrew(Integer crewId) {
        log.info("getCrew: crewId: {}", crewId);
        Crew crew;
        try {
            crew = raceService.getCrewById(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewMapper.map(crew));
    }

    /**
     * Ziska vysledky posadky z pretekov
     * @param crewId id posadky (required)
     * @return vysleky
     */
    @Override
    public ResponseEntity<CrewResultsDetailDto> getCrewResults(Integer crewId) {
        log.info("getCrewResults: crewId: {}", crewId);
        Crew crew;
        try {
            crew = raceService.getCrewById(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewMapper.mapResults(crew));
    }

    /**
     * Ziska informacie o pretekoch
     * @param raceId id pretekov (required)
     * @return informacie o pretekoch
     */
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

    /**
     * Ziska trat pretekov
     * @param raceId id pretekov (required)
     * @return DTO trate
     */
    @Override
    public ResponseEntity<CourseDetailDto> getRacesCourse(Integer raceId) {
        log.info("getRacesCourse: raceId: {}", raceId);
        RaceCourse raceCourse = raceService.getRaceCourse(raceId);
        return ResponseEntity.ok(raceMapper.map(raceCourse));
    }

    /**
     * Ziska lode, ktore moze aktualne prihlaseny pouzivatel prihlasit a preteky
     * @param raceId id pretekov (required)
     * @return zoznam lodi
     */
    @Override
    public ResponseEntity<CrewDetailListDto> getShipsForRace(Integer raceId) {
        log.info("getShipsForRace: raceId: {}", raceId);
        CrewDetailListDto crewDetailListDto;
        try {
            List<Ship> ships = raceService.getShipsForRace(raceId);
            crewDetailListDto = crewMapper.mapShipsListDto(ships);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewDetailListDto);
    }

    /**
     * Odstrani aktualne prihlaseneho pouzivatela z posadky
     * @param crewId id posadky (required)
     */
    @Override
    public ResponseEntity<Void> leaveCrew(Integer crewId) {
        log.info("leaveCrew: crewId: {}", crewId);
        try {
            raceService.leaveCrew(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Odstranie posadku z pretehov
     * @param crewId id posadky (required)
     */
    @Override
    public ResponseEntity<Void> removeCrewFromRace(Integer crewId) {
        log.info("removeCrewFromRace: crewId: {}", crewId);
        try {
            raceService.removeCrew(crewId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Odstani pouzivatela z posadky
     * @param crewUserId id registracie do posadky (required)
     */
    @Override
    public ResponseEntity<Void> removeUserFromCrew(Integer crewUserId) {
        log.info("removeUserFromCrew: crewUserId: {}", crewUserId);
        try {
            raceService.removeUserFromCrew(crewUserId);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Prihlasi lode do pretekov vytvorenim posadok
     * @param raceId id pretekov (required)
     * @param shipSignUpListDto zoznam id lodi (required)
     * @return zoznam DTO vytvorenych posadok
     */
    @Override
    public ResponseEntity<CrewDetailListDto> signUpShipsForRace(Integer raceId, ShipSignUpListDto shipSignUpListDto) {
        log.info("signUpShipsForRace: raceId: {}, shipSignUpListDto: {}", raceId, shipSignUpListDto);
        CrewDetailListDto crewDetailListDto;
        try {
            List<Crew> crews = raceService.signUpShipsForRace(raceId, shipSignUpListDto.getShips());
            crewDetailListDto = crewMapper.mapCrewsListDto(crews);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewDetailListDto);
    }

    /**
     * Nahra posadke vysledky
     * @param crewId id posadky (required)
     * @param crewResultsUpdateDto vvysledky na ulozenie (required)
     * @return ulozene vysledky
     */
    @Override
    public ResponseEntity<CrewResultsDetailDto> updateCrewResults(Integer crewId,
            CrewResultsUpdateDto crewResultsUpdateDto) {
        Crew crew = crewMapper.map(crewResultsUpdateDto);
        try {
            crew = raceService.updateResults(crewId, crew);
        } catch (HttpException e) {
            return HttpExceptionHandler.resolve(e);
        }
        return ResponseEntity.ok(crewMapper.mapResults(crew));
    }

    /**
     * Upravi informacie o pretekoch
     * @param raceId id pretekov (required)
     * @param updateRaceDto nove informacie (required)
     * @return DTO pretekov
     */
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
