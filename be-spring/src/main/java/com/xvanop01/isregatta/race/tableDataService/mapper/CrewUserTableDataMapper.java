package com.xvanop01.isregatta.race.tableDataService.mapper;

import com.xvanop01.isregatta.api.race.model.UserRaceInfoDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.tableDataService.view.CrewUserView;
import org.mapstruct.Mapper;

/**
 * CrewUserTableDataMapper
 * Mapper pre CrewUserTableDataService
 * @author 2024 Peter Vano
 */
@Mapper(componentModel = "spring")
public abstract class CrewUserTableDataMapper extends TableDataResponseMapper<CrewUserView, UserRaceInfoDto> {

}
