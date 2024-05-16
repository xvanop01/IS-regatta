package com.xvanop01.isregatta.ship.tableDataService;

import com.xvanop01.isregatta.api.ship.model.ShipDetailDto;
import com.xvanop01.isregatta.base.exception.HttpException;
import com.xvanop01.isregatta.base.security.PrincipalService;
import com.xvanop01.isregatta.base.security.SecurityService;
import com.xvanop01.isregatta.base.support.template.TableData;
import com.xvanop01.isregatta.base.support.template.TableDataService;
import com.xvanop01.isregatta.ship.tableDataService.filter.ShipTableDataFilter;
import com.xvanop01.isregatta.ship.tableDataService.mapper.ShipTableDataMapper;
import com.xvanop01.isregatta.ship.tableDataService.view.ShipView;
import com.xvanop01.isregatta.ship.tableDataService.view.ShipView_;
import com.xvanop01.isregatta.ship.tableDataService.viewRepository.ShipViewRepository;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ShipTableDataService
 * Servis pre tabulky s lodami
 * @author 2024 Peter Vano
 */
@TableData("ship-table")
public class ShipTableDataService extends TableDataService<ShipView, ShipViewRepository, ShipTableDataFilter,
        ShipDetailDto, ShipTableDataMapper> {

    private final SecurityService securityService;

    public ShipTableDataService(ShipViewRepository repository, ShipTableDataMapper mapper,
            SecurityService securityService) {
        super(repository, mapper);
        this.securityService = securityService;
    }

    /**
     * Prepisuje metodu pre naplnanie znacky, ci moze upravovat informacie o lodiach
     * @param pageable definicia parametrov strany
     * @param filter inicializovany filter
     * @return strana udajov podla specifikacie
     */
    protected Page<ShipView> fetch(Pageable pageable, Object filter) {
        final Integer userId = PrincipalService.getPrincipalId();
        boolean isAdmin;
        try {
            isAdmin = securityService.isAdmin();
        } catch (Exception ignore) {
            isAdmin = false;
        }
        final boolean isAdminFinal = isAdmin;
        Page<ShipView> shipViews = repository.findAll(getSpecification(), pageable);
        shipViews.forEach(shipView -> {
            if (isAdminFinal || shipView.getUserId().equals(userId)) { // ak admin, alebo majitel lode
                shipView.setCanChange(true);
            }
        });
        return shipViews;
    }

    @Override
    protected void doFilter(Object filter) {
        if (filter instanceof ShipTableDataFilter f) {
            // filtrovanie podla id majitela
            if (f.userId != null) {
                specAnd(((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(ShipView_.userId), f.userId))
                );
            }
            // filtrovanie podla mena lode
            if (f.name != null && !f.name.isEmpty()) {
                String searchFormatted = "%" + f.name.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.name), searchFormatted)
                );
            }
            // filtrovanie podla registracie lode
            if (f.registration != null && !f.registration.isEmpty()) {
                String searchFormatted = "%" + f.registration.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.registration), searchFormatted)
                );
            }
            // filtrovanie podla civilneho mena majitela / pouzivatelskeho mena
            if (f.ownerName != null && !f.ownerName.isEmpty()) {
                String searchFormatted = "%" + f.ownerName.toLowerCase() + "%";
                specAnd((root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(ShipView_.ownerName), searchFormatted)
                );
            }
        }
    }
}
