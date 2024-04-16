package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CreateUpdateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.api.race.model.RaceUserInfoDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.RaceSigned;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class RaceMapper extends TableDataResponseMapper<Race, RaceDetailDto> {

    @Mappings({
            @Mapping(target = "mainOrganizerId", source = "organizer.id"),
            @Mapping(target = "mainOrganizerName", source = "organizer.fullName")
    })
    public abstract RaceDetailDto map(Race race);

    public abstract Race map(CreateUpdateRaceDto dto);

    @Mappings({
            @Mapping(target = "raceId", source = "race.id"),
            @Mapping(target = "userId", source = "user.id"),
    })
    public abstract RaceUserInfoDto map(RaceSigned raceSigned);
}
