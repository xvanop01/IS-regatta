package com.xvanop01.isregatta.ship.tableDataService.viewRepository;

import com.xvanop01.isregatta.ship.tableDataService.view.ShipView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ShipViewRepository
 * Repozitar zabezpecujuci poziadavky na db pre ShipTableDataService
 * @author 2024 Peter Vano
 */
public interface ShipViewRepository extends JpaRepository<ShipView, Integer>, JpaSpecificationExecutor<ShipView> {

}
