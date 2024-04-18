package com.xvanop01.isregatta.ship.mapper;

import com.xvanop01.isregatta.api.ship.model.CreateUpdateShipDto;
import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.ship.model.Ship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class ShipMapper extends TableDataResponseMapper<Ship, ShipDetailDto> {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true)
    })
    public abstract Ship map(CreateUpdateShipDto dto);

    @Mappings({
            @Mapping(target = "ownerName", source = "owner.fullName")
    })
    public abstract ShipDetailDto map(Ship ship);
}
