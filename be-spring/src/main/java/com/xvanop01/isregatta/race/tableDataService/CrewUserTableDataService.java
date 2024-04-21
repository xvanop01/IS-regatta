package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.UserRaceInfoDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.tableDataService.filter.CrewUserTableDataFilter;
import com.xvanop01.isregatta.race.tableDataService.mapper.CrewUserTableDataMapper;
import com.xvanop01.isregatta.race.tableDataService.view.CrewUserView;
import com.xvanop01.isregatta.race.tableDataService.view.CrewUserView_;
import com.xvanop01.isregatta.race.tableDataService.viewRepository.CrewUserViewRepository;

@TableData("crew-user-table")
public class CrewUserTableDataService extends TableDataService<CrewUserView, CrewUserViewRepository,
        CrewUserTableDataFilter, UserRaceInfoDto, CrewUserTableDataMapper> {

    public CrewUserTableDataService(CrewUserViewRepository repository, CrewUserTableDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof CrewUserTableDataFilter f) {
            if (f.crewId != null) {
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(CrewUserView_.crewId), f.crewId)
                );
            }
            if (f.raceId != null) {
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(CrewUserView_.raceId), f.raceId)
                );
            }
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(CrewUserView_.name), searchFormatted)
                );
            }
        }
    }
}
