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
@Subselect("SELECT crew_user.id, "
        + "  crew.race_id,"
        + "  crew_user.crew_id, "
        + "  ship.name as ship_name, "
        + "  IFNULL(user.full_name, user.username) as name, "
        + "  user.email, "
        + "  crew_user.status "
        + "FROM race_crew_user crew_user "
        + "  LEFT JOIN user_user user ON crew_user.user_id = user.id "
        + "  LEFT JOIN race_crew crew ON crew_user.crew_id = crew.id "
        + "  LEFT JOIN ship_ship ship ON crew.ship_id = ship.id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CrewUserView {

    @Id
    private Integer id;

    private Integer raceId;

    private Integer crewId;

    private String shipName;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;
}
