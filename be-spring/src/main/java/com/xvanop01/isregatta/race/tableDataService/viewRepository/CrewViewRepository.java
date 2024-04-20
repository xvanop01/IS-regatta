package com.xvanop01.isregatta.race.tableDataService.viewRepository;

import com.xvanop01.isregatta.race.tableDataService.view.CrewView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewViewRepository extends JpaRepository<CrewView, Integer>, JpaSpecificationExecutor<CrewView> {

    Page<CrewView> findAllByRaceId(Integer raceId, Pageable pageable, Specification<CrewView> specification);
}
