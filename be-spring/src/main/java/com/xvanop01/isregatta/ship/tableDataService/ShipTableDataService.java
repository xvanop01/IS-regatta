package com.xvanop01.isregatta.ship.tableDataService;

import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.ship.mapper.ShipMapper;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.repository.ShipRepository;
import com.xvanop01.isregatta.ship.tableDataService.filter.ShipTableDataFilter;

@TableData("ship-table")
public class ShipTableDataService
        extends TableDataService<Ship, ShipRepository, ShipTableDataFilter, ShipDetailDto, ShipMapper> {

    public ShipTableDataService(ShipRepository repository, ShipMapper mapper) {
        super(repository, mapper);
    }
}
