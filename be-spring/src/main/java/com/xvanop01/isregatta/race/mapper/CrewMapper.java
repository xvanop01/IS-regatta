package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.api.race.model.CrewDetailListDto;
import com.xvanop01.isregatta.api.race.model.CrewResultsDetailDto;
import com.xvanop01.isregatta.api.race.model.CrewResultsUpdateDto;
import com.xvanop01.isregatta.api.race.model.RegistrationStatusEnum;
import com.xvanop01.isregatta.api.race.model.UserRaceInfoDto;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.CrewUser;
import com.xvanop01.isregatta.race.model.RegistrationStatus;
import com.xvanop01.isregatta.ship.model.Ship;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * CrewMapper
 * Zabezpecuje mapovanie medzi uzivatelmi, poadkami a lodami
 * @author 2024 Peter vano
 */
@Mapper(componentModel = "spring")
public abstract class CrewMapper {

    @Mappings({
            @Mapping(target = "raceId", source = "race.id"),
            @Mapping(target = "shipName", source = "ship.name"),
            @Mapping(target = "shipRegistration", source = "ship.registration"),
            @Mapping(target = "raceName", source = "race.name"),
            @Mapping(target = "shipOwnerId", source = "ship.owner.id"),
            @Mapping(target = "shipOwnerName", expression = "java(crew.getShip().getOwner().getFullName() == null ||"
                    + " crew.getShip().getOwner().getFullName().isEmpty() ? "
                            + "crew.getShip().getOwner().getUsername() : crew.getShip().getOwner().getFullName())")
    })
    public abstract CrewDetailDto map(Crew crew);

    @Mappings({
            @Mapping(target = "shipName", source = "ship.name"),
            @Mapping(target = "shipRegistration", source = "ship.registration"),
            @Mapping(target = "shipOwnerName", ignore = true),
            @Mapping(target = "shipOwnerId", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "raceId", ignore = true),
            @Mapping(target = "raceName", ignore = true),
    })
    public abstract CrewDetailDto map(Ship ship);

    public abstract RegistrationStatusEnum map(RegistrationStatus registrationStatus);

    @Mappings({
            @Mapping(target = "raceId", source = "crew.race.id"),
            @Mapping(target = "crewId", source = "crew.id"),
            @Mapping(target = "shipName", source = "crew.ship.name"),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "email", ignore = true)
    })
    public abstract UserRaceInfoDto map(CrewUser crewUser);

    @Mappings({
            @Mapping(target = "finishingTime", source = "time"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "ship", ignore = true),
            @Mapping(target = "race", ignore = true),
            @Mapping(target = "status", ignore = true),
    })
    public abstract Crew map(CrewResultsUpdateDto dto);

    @Mappings({
            @Mapping(target = "crewName", source = "ship.name"),
            @Mapping(target = "time", source = "finishingTime")
    })
    public abstract CrewResultsDetailDto mapResults(Crew crew);

    public abstract List<CrewDetailDto> mapCrewsList(List<Crew> crews);

    public abstract List<CrewDetailDto> mapShipsList(List<Ship> ships);

    public CrewDetailListDto mapCrewsListDto(List<Crew> crews) {
        if (crews != null && !crews.isEmpty()) {
            CrewDetailListDto dto = new CrewDetailListDto();
            dto.setCrews(mapCrewsList(crews));
            return dto;
        }
        return null;
    }

    public CrewDetailListDto mapShipsListDto(List<Ship> ships) {
        if (ships != null && !ships.isEmpty()) {
            CrewDetailListDto dto = new CrewDetailListDto();
            dto.setCrews(mapShipsList(ships));
            return dto;
        }
        return null;
    }
}
