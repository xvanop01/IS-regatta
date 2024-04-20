package com.xvanop01.isregatta.race.model;

import com.xvanop01.isregatta.ship.model.Ship;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "race_crew")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @Enumerated(EnumType.STRING)
    private CrewStatus status;

    private Integer position;

    private LocalDateTime finishingTime;
}
