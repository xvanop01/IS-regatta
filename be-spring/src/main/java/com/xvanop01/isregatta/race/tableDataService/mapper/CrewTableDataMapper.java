package com.xvanop01.isregatta.race.tableDataService.mapper;

import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.tableDataService.view.CrewView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CrewTableDataMapper extends TableDataResponseMapper<CrewView, CrewDetailDto> {

}
