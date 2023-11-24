package com.useclient.zenvironment.model.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(length = 1000)
    private String imageUrl;
    private double averageOxygenProductionInKilogramsPerDay;
    private double averageCO2FixationInKilogramsPerDay;
    private double averageCropPerCycle;
    private double averageMonthsUntilHarvest;
    private double waterInLiterPerWeek;
    private String harvestUnit;



}
