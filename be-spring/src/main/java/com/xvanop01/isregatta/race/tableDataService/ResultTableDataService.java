package com.xvanop01.isregatta.race.tableDataService;

import com.xvanop01.isregatta.api.race.model.CrewResultsDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.race.tableDataService.filter.ResultTableDataFilter;
import com.xvanop01.isregatta.race.tableDataService.mapper.ResultTableDataMapper;
import com.xvanop01.isregatta.race.tableDataService.view.ResultView;
import com.xvanop01.isregatta.race.tableDataService.viewRepository.ResultViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ResultTableDataService
 * Servis pre tabulky s vysledkami
 * @author 2024 Peter Vano
 */
@TableData("result-table")
public class ResultTableDataService extends TableDataService<ResultView, ResultViewRepository, ResultTableDataFilter,
        CrewResultsDetailDto, ResultTableDataMapper> {

    public ResultTableDataService(ResultViewRepository repository, ResultTableDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Page<ResultView> fetch(Pageable pageable, Object filter) {
        if (filter instanceof ResultTableDataFilter f) {
            // filtrovanie podla id pretekov
            return repository.findAllByRaceIdOrderByPosition(f.raceId, pageable);
        }
        return super.fetch(pageable, filter);
    }
}
