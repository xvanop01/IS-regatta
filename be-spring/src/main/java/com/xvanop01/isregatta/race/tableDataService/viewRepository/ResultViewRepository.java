package com.xvanop01.isregatta.race.tableDataService.viewRepository;

import com.xvanop01.isregatta.race.tableDataService.view.ResultView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResultViewRepository extends JpaRepository<ResultView, Integer>, JpaSpecificationExecutor<ResultView> {

    Page<ResultView> findAllByRaceIdOrderByPosition(Integer raceId, Pageable pageable);
}
