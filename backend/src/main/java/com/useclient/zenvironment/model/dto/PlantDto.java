package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PlantDto {
    private UUID id;
    private PlantTypeDto plantType;
    private UUID gardenId;
    private double estimatedProducedOxygenInKilograms;
    private double estimatedFixatedCO2InKilograms;
    private LocalDate plantedAt;
    private LocalDate uprootedAt;
}
