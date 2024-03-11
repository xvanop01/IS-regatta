package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.mapper.RaceMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.repository.RaceRepository;

@TableData("race-table")
public class RaceTableDataService extends TableDataService<Race, RaceRepository, RaceDetailDto, RaceMapper> {

    public RaceTableDataService(RaceRepository repository, RaceMapper mapper) {
        super(repository, mapper);
    }
}
