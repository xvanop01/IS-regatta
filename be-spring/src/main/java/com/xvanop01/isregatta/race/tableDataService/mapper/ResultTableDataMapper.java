package com.xvanop01.isregatta.race.tableDataService.mapper;

import com.xvanop01.isregatta.api.race.model.CrewResultsDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.race.tableDataService.view.ResultView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ResultTableDataMapper extends TableDataResponseMapper<ResultView, CrewResultsDetailDto> {

}
