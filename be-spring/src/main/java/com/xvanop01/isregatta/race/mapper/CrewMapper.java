package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.api.race.model.CrewDetailListDto;
import com.xvanop01.isregatta.api.race.model.CrewStatusEnum;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.CrewStatus;
import com.xvanop01.isregatta.ship.model.Ship;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class CrewMapper {

    @Mappings({
            @Mapping(target = "shipName", source = "ship.name"),
            @Mapping(target = "shipRegistration", source = "ship.registration"),
            @Mapping(target = "shipOwnerName", expression = "java(crew.getShip().getOwner().getFullName() == null ||"
                    + " crew.getShip().getOwner().getFullName().isEmpty() ? "
                            + "crew.getShip().getOwner().getUsername() : crew.getShip().getOwner().getFullName())"),
    })
    public abstract CrewDetailDto map(Crew crew);

    @Mappings({
            @Mapping(target = "shipName", source = "ship.name"),
            @Mapping(target = "shipRegistration", source = "ship.registration"),
            @Mapping(target = "shipOwnerName", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    public abstract CrewDetailDto map(Ship ship);

    public abstract CrewStatusEnum map(CrewStatus crewStatus);

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
