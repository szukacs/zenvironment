package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlantTypeDto {
    private UUID id;
    private String name;
    private String imageUrl;
    private double averageOxygenProductionInKilogramsPerDay;
    private double averageCO2FixationInKilogramsPerDay;
    private String harvestUnit;
}
