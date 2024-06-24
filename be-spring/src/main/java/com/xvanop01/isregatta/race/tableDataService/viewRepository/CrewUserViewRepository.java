package com.xvanop01.isregatta.race.tableDataService.viewRepository;

import com.xvanop01.isregatta.race.tableDataService.view.CrewUserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * CrewUserViewRepository
 * Repozitar zabezpecujuci poziadavky na db pre CrewUserTableDataService
 * @author 2024 Peter Vano
 */
public interface CrewUserViewRepository extends JpaRepository<CrewUserView, Integer>,
        JpaSpecificationExecutor<CrewUserView> {

}
