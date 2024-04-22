package com.xvanop01.isregatta.race.tableDataService.view;

import com.xvanop01.isregatta.race.model.RegistrationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT crew.id, "
        + "  crew.race_id, "
        + "  ship.name as ship_name, "
        + "  ship.registration as ship_registration, "
        + "  owner.id as ship_owner_id, "
        + "  IFNULL(owner.full_name, owner.username) as ship_owner_name, "
        + "  race.name as race_name, "
        + "  crew.status "
        + "FROM race_crew crew "
        + "  LEFT JOIN ship_ship ship ON crew.ship_id = ship.id "
        + "  LEFT JOIN user_user owner ON ship.owner_id = owner.id "
        + "  LEFT JOIN race_race race ON crew.race_id = race.id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CrewView {

    @Id
    private Integer id;

    private Integer raceId;

    private String shipName;

    private String shipRegistration;

    private Integer shipOwnerId;

    private String shipOwnerName;

    private String raceName;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

}
