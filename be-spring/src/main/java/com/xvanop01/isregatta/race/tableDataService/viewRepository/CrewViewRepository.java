package com.xvanop01.isregatta.race.tableDataService.viewRepository;

import com.xvanop01.isregatta.race.tableDataService.view.CrewView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * CrewViewRepository
 * Repozitar zabezpecujuci poziadavky na db pre CrewTableDataService
 * @author 2024 Peter Vano
 */
public interface CrewViewRepository extends JpaRepository<CrewView, Integer>, JpaSpecificationExecutor<CrewView> {

}
