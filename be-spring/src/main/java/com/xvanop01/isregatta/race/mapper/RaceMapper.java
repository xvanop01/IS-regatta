package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CreateUpdateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.model.Race;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class RaceMapper extends TableDataResponseMapper<Race, RaceDetailDto> {

    @Mappings({
            @Mapping(target = "mainOrganizerId", source = "organizer.id"),
            @Mapping(target = "mainOrganizerName", expression =
                    "java(race.getOrganizer().getFullName() == null || race.getOrganizer().getFullName().isEmpty() ? "
                    + "race.getOrganizer().getUsername() : race.getOrganizer().getFullName())")
    })
    public abstract RaceDetailDto map(Race race);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "organizer", ignore = true)
    })
    public abstract Race map(CreateUpdateRaceDto dto);
}
