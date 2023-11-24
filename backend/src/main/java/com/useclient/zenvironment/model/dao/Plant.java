package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    private PlantType plantType;
    @ManyToOne
    private Garden garden;
    private LocalDate plantedAt;
    private LocalDate uprootedAt;

    public double getAllProducedOxygenInKilograms(){
        return this.plantType.getAverageOxygenProductionInKilogramsPerDay() * DAYS.between(plantedAt, LocalDate.now());
    }

    public double getAllFixatedCO2InKilograms(){
        return this.plantType.getAverageCO2FixationInKilogramsPerDay() * DAYS.between(plantedAt, LocalDate.now());
    }

    public double getAllWaterConsumptionInLiters(){
        return this.plantType.getWaterInLiterPerWeek() / 7 * DAYS.between(plantedAt, LocalDate.now());
    }

    public int getDaysTillHarvest(){
        return (int) ((int) Math.round(this.plantType.getAverageMonthsUntilHarvest() * 30) - DAYS.between(plantedAt, LocalDate.now()));
    }
}
