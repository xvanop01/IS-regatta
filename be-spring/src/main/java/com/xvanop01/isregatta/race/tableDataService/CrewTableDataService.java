package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.tableDataService.mapper.CrewTableDataMapper;
import com.xvanop01.isregatta.race.tableDataService.view.CrewView;
import com.xvanop01.isregatta.race.tableDataService.view.CrewView_;
import com.xvanop01.isregatta.race.tableDataService.viewRepository.CrewViewRepository;
import com.xvanop01.isregatta.race.tableDataService.filter.CrewTableDataFilter;

@TableData("crew-table")
public class CrewTableDataService extends TableDataService<CrewView, CrewViewRepository, CrewTableDataFilter,
        CrewDetailDto, CrewTableDataMapper> {

    public CrewTableDataService(CrewViewRepository repository, CrewTableDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof CrewTableDataFilter f) {
            if (f.raceId != null) {
                specAnd(((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(CrewView_.raceId), f.raceId))
                );
            }
            if (f.shipName != null && !f.shipName.isEmpty()) {
                String searchFormatted = "%" + f.shipName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(CrewView_.shipName), searchFormatted)
                );
            }
            if (f.shipRegistration != null && !f.shipRegistration.isEmpty()) {
                String searchFormatted = "%" + f.shipRegistration.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(CrewView_.shipRegistration), searchFormatted)
                );
            }
            if (f.shipOwnerName != null && !f.shipOwnerName.isEmpty()) {
                String searchFormatted = "%" + f.shipOwnerName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(CrewView_.shipOwnerName), searchFormatted)
                );
            }
        }
    }
}
