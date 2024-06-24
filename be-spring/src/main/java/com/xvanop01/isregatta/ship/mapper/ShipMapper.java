package com.xvanop01.isregatta.ship.mapper;

import com.xvanop01.isregatta.api.ship.model.CreateUpdateShipDto;
import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableDataResponseMapper;
import com.xvanop01.isregatta.ship.model.Ship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * ShipMapper
 * Mapper zabezpecujuci mapovanie lodi
 * @author 2024 Peter Vano
 */
@Mapper(componentModel = "spring")
public abstract class ShipMapper extends TableDataResponseMapper<Ship, ShipDetailDto> {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true)
    })
    public abstract Ship map(CreateUpdateShipDto dto);

    @Mappings({
            // ziskanie civilneho mena, ak nie je tak pouziavatelskeho mena
            @Mapping(target = "ownerName", expression =
                    "java(ship.getOwner().getFullName() == null || ship.getOwner().getFullName().isEmpty() ? "
                            + "ship.getOwner().getUsername() : ship.getOwner().getFullName())"),
            @Mapping(target = "canChange", ignore = true)
    })
    public abstract ShipDetailDto map(Ship ship);
}
