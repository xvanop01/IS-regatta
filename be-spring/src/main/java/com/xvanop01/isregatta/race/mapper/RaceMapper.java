package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CreateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailListDto;
import com.xvanop01.isregatta.api.race.model.UpdateRaceDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.model.Race;
import org.mapstruct.Mapper;

import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class RaceMapper extends TableDataResponseMapper<Race, RaceDetailDto> {

    @Mappings({
            @Mapping(target = "mainOrganizerId", source = "organizer.id")
    })
    public abstract RaceDetailDto map(Race race);

    public abstract List<RaceDetailDto> map(List<Race> raceList);

    public abstract Race map(CreateRaceDto dto);

    public abstract Race map(UpdateRaceDto dto);

    public RaceDetailListDto mapList(List<Race> raceList) {
        RaceDetailListDto dto = new RaceDetailListDto();
        dto.setRaces(map(raceList));
        return dto;
    }
}
