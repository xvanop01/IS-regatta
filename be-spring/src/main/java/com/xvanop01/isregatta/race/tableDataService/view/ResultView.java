package com.xvanop01.isregatta.race.tableDataService.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

/**
 * ResultView
 * Neperzistovana entita zdruzujuca informacie pre ResultTableDataService
 * @author 2024 Peter Vano
 */
@Entity
@Immutable
@Subselect("SELECT crew.id, "
        + "  crew.race_id, "
        + "  ship.name as crew_name, "
        + "  crew.position, "
        + "  crew.finishing_time as time "
        + "FROM race_crew crew "
        + "  LEFT JOIN ship_ship ship ON crew.ship_id = ship.id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultView {

    @Id
    private Integer id;

    private Integer raceId;

    private String crewName;

    private Integer position;

    private String time;
}
