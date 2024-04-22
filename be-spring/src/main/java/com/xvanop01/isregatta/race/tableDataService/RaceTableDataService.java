package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.RaceDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.mapper.RaceMapper;
import com.xvanop01.isregatta.race.model.Race;
import com.xvanop01.isregatta.race.model.Race_;
import com.xvanop01.isregatta.race.repository.RaceRepository;
import com.xvanop01.isregatta.race.tableDataService.filter.RaceTableDataFilter;
import java.time.temporal.TemporalAdjusters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@TableData("race-table")
public class RaceTableDataService
        extends TableDataService<Race, RaceRepository, RaceTableDataFilter, RaceDetailDto, RaceMapper> {

    public RaceTableDataService(RaceRepository repository, RaceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof RaceTableDataFilter f) {
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Race_.name), searchFormatted)
                );
            }
            if (f.date != null) {
                specAnd((root, query, criteriaBuilder) -> criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(Race_.date), f.date.with(TemporalAdjusters.firstDayOfMonth())),
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(Race_.date), f.date.with(TemporalAdjusters.lastDayOfMonth())))
                );
            }
        }
    }

    @Override
    protected Page<Race> fetch(Pageable pageable, Object filter) {
        if (filter instanceof RaceTableDataFilter f) {
            if (f.userId != null) {
                Order order = pageable.getSort().iterator().next();
                if (order.getProperty().equals("signUpUntil")) {
                    Sort sort = Sort.by(order.getDirection(), "sign_up_until");
                    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
                }
                return repository.findByUserId(f.userId, getSpecification(), pageable);
            }
        }
        return super.fetch(pageable, filter);
    }
}
