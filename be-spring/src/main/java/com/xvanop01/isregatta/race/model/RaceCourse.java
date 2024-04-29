package com.xvanop01.isregatta.race.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "race_race_course")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RaceCourse {

    @Id
    private Integer raceId;

    private BigDecimal buoy1Long;

    private BigDecimal buoy1Lat;

    private BigDecimal buoy2Long;

    private BigDecimal buoy2Lat;

    private BigDecimal buoy3Long;

    private BigDecimal buoy3Lat;

    private BigDecimal centerLong;

    private BigDecimal centerLat;

    private Integer zoom;

    private Integer windAngle;
}
