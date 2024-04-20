package com.xvanop01.isregatta.ship.tableDataService;

import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.ship.tableDataService.filter.ShipTableDataFilter;
import com.xvanop01.isregatta.ship.tableDataService.mapper.ShipTableDataMapper;
import com.xvanop01.isregatta.ship.tableDataService.view.ShipView;
import com.xvanop01.isregatta.ship.tableDataService.view.ShipView_;
import com.xvanop01.isregatta.ship.tableDataService.viewRepository.ShipViewRepository;

@TableData("ship-table")
public class ShipTableDataService extends TableDataService<ShipView, ShipViewRepository, ShipTableDataFilter,
        ShipDetailDto, ShipTableDataMapper> {

    public ShipTableDataService(ShipViewRepository repository, ShipTableDataMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof ShipTableDataFilter f) {
            if (f.ownerId != null) {
                specAnd(((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(ShipView_.ownerId), f.ownerId))
                );
            }
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.name), searchFormatted)
                );
            }
            if (f.registration != null && !f.registration.isEmpty()) {
                String searchFormatted = "%" + f.registration.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.registration), searchFormatted)
                );
            }
            if (f.ownerName != null && !f.ownerName.isEmpty()) {
                String searchFormatted = "%" + f.ownerName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.ownerName), searchFormatted)
                );
            }
        }
    }
}
