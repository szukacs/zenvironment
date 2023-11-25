package com.useclient.zenvironment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlantSummary {
    private PlantTypeDto plantType;
    private int plantCount;
    private double allProducedOxygenInKilograms;
    private double allFixatedCO2InKilograms;
}
