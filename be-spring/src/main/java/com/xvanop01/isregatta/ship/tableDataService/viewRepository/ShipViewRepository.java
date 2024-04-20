package com.xvanop01.isregatta.ship.tableDataService.viewRepository;

import com.xvanop01.isregatta.ship.tableDataService.view.ShipView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShipViewRepository extends JpaRepository<ShipView, Integer>, JpaSpecificationExecutor<ShipView> {

}
