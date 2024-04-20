package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.CrewDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.mapper.CrewMapper;
import com.xvanop01.isregatta.race.model.Crew;
import com.xvanop01.isregatta.race.model.Crew_;
import com.xvanop01.isregatta.ship.model.Ship_;
import com.xvanop01.isregatta.race.repository.CrewRepository;
import com.xvanop01.isregatta.race.tableDataService.filter.CrewTableDataFilter;
import com.xvanop01.isregatta.user.model.User_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@TableData("crew-table")
public class CrewTableDataService
        extends TableDataService<Crew, CrewRepository, CrewTableDataFilter, CrewDetailDto, CrewMapper> {

    public CrewTableDataService(CrewRepository repository, CrewMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Page<Crew> fetch(Pageable pageable, Object filter) {
        if (filter instanceof CrewTableDataFilter f) {
            if (f.raceId != null) {
                return repository.findAllByRaceId(f.raceId, getSpecification(), pageable);
            }
        }
        return super.fetch(pageable, filter);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof CrewTableDataFilter f) {
            if (f.shipName != null && !f.shipName.isEmpty()) {
                String searchFormatted = "%" + f.shipName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Crew_.ship).get(Ship_.name), searchFormatted)
                );
            }
            if (f.shipRegistration != null && !f.shipRegistration.isEmpty()) {
                String searchFormatted = "%" + f.shipRegistration.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Crew_.ship).get(Ship_.registration), searchFormatted)
                );
            }
            if (f.shipOwnerName != null && !f.shipOwnerName.isEmpty()) {
                String searchFormatted = "%" + f.shipOwnerName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Crew_.ship).get(Ship_.owner).get(User_.fullName), searchFormatted)
                );
            }
        }
    }
}
