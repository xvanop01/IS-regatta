package com.xvanop01.isregatta.ship.tableDataService.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT ship.id, "
        + "  owner.id as user_id, "
        + "  false as can_change, "
        + "  ship.name, "
        + "  ship.registration, "
        + "  IFNULL(owner.full_name, owner.username) as owner_name "
        + "FROM ship_ship ship "
        + "  LEFT JOIN user_user owner ON ship.owner_id = owner.id ")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShipView {

    @Id
    private Integer id;

    private Integer userId;

    private Boolean canChange;

    private String name;

    private String registration;

    private String ownerName;

}
