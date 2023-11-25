package com.useclient.zenvironment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PlantDto {
    private UUID id;
    private UUID gardenId;
    private int x;
    private int y;
    private PlantTypeDto plantType;
    private double allProducedOxygenInKilograms;
    private double allFixatedCO2InKilograms;
    private double allWaterConsumptionInLiters;
    private double allHarvestedAmount;
    private int daysTillHarvest;
    private LocalDate plantedAt;
    private LocalDate uprootedAt;
    private List<HarvestDto> harvests;

}
