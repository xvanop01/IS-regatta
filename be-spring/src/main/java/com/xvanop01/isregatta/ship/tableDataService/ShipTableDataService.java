package com.xvanop01.isregatta.ship.tableDataService;

import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.ship.mapper.ShipMapper;
import com.xvanop01.isregatta.ship.model.Ship;
import com.xvanop01.isregatta.ship.model.Ship_;
import com.xvanop01.isregatta.ship.repository.ShipRepository;
import com.xvanop01.isregatta.ship.tableDataService.filter.ShipTableDataFilter;
import com.xvanop01.isregatta.user.model.User_;

@TableData("ship-table")
public class ShipTableDataService
        extends TableDataService<Ship, ShipRepository, ShipTableDataFilter, ShipDetailDto, ShipMapper> {

    public ShipTableDataService(ShipRepository repository, ShipMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof ShipTableDataFilter f) {
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Ship_.name), searchFormatted)
                );
            }
            if (f.registration != null && !f.registration.isEmpty()) {
                String searchFormatted = "%" + f.registration.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Ship_.registration), searchFormatted)
                );
            }
            if (f.ownerName != null && !f.ownerName.isEmpty()) {
                String searchFormatted = "%" + f.ownerName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(Ship_.owner).get(User_.fullName), searchFormatted)
                );
            }
        }
    }
}
