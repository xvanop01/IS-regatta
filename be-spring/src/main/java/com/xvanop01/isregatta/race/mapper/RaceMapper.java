package com.xvanop01.isregatta.race.mapper;

import com.xvanop01.isregatta.api.race.model.CourseDetailDto;
import com.xvanop01.isregatta.api.race.model.CreateUpdateCourseDto;
import com.xvanop01.isregatta.api.race.model.CreateUpdateRaceDto;
import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.RaceCourse;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * RaceMapper
 * Zabezpecuje mapovanie pretekov
 * @author 2024 Peter Vano
 */
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

    @Mappings({
            @Mapping(target = "buoy1Long", source = "buoy1.longitude"),
            @Mapping(target = "buoy1Lat", source = "buoy1.latitude"),
            @Mapping(target = "buoy2Long", source = "buoy2.longitude"),
            @Mapping(target = "buoy2Lat", source = "buoy2.latitude"),
            @Mapping(target = "buoy3Long", source = "buoy3.longitude"),
            @Mapping(target = "buoy3Lat", source = "buoy3.latitude"),
            @Mapping(target = "centerLong", source = "center.longitude"),
            @Mapping(target = "centerLat", source = "center.latitude"),
            @Mapping(target = "raceId", ignore = true)
    })
    public abstract RaceCourse map(CreateUpdateCourseDto dto);

    @Mappings({
            @Mapping(target = "buoy1.longitude", source = "buoy1Long"),
            @Mapping(target = "buoy1.latitude", source = "buoy1Lat"),
            @Mapping(target = "buoy2.longitude", source = "buoy2Long"),
            @Mapping(target = "buoy2.latitude", source = "buoy2Lat"),
            @Mapping(target = "buoy3.longitude", source = "buoy3Long"),
            @Mapping(target = "buoy3.latitude", source = "buoy3Lat"),
            @Mapping(target = "center.longitude", source = "centerLong"),
            @Mapping(target = "center.latitude", source = "centerLat")
    })
    public abstract CourseDetailDto map(RaceCourse course);
}
