package com.xvanop01.isregatta.ship.tableDataService.mapper;

import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.ship.tableDataService.view.ShipView;
import org.mapstruct.Mapper;

/**
 * ShipTableDataMapper
 * Mapper pre ShipTableDataService
 * @author 2024 Peter Vano
 */
@Mapper(componentModel = "spring")
public abstract class ShipTableDataMapper extends TableDataResponseMapper<ShipView, ShipDetailDto> {

}
