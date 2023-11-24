package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MainMapper {

    PlantTypeDto toDto(PlantType plantType);

    @Mapping(target = "gardenId", source = "garden.id")
    PlantDto toDto(Plant plant);

    List<PlantDto> toPlantDtoList(List<Plant> plants);

    MinimalCommunity toMinDto(Community community);

    CommunityDto toDto(Community community);

    @Mapping(target = "estimatedProducedOxygenInKilograms", source = "garden.plants", qualifiedByName = "summarizeOxygenProduction")
    @Mapping(target = "estimatedFixatedCO2InKilograms", source = "garden.plants", qualifiedByName = "summarizeCO2Fixation")
    GardenDto toDto(Garden garden);

    @Named("summarizeOxygenProduction")
    default double summarizeOxygenProduction(List<Plant> plants) {
        return plants.stream().map(Plant::getAllProducedOxygenInKilograms).reduce(0.0, Double::sum);
    }

    @Named("summarizeCO2Fixation")
    default double summarizeCO2Fixation(List<Plant> plants) {
        return plants.stream().map(Plant::getAllFixatedCO2InKilograms).reduce(0.0, Double::sum);
    }

    @Named("summarizeWaterConsumption")
    default double summarizeWaterConsumption(List<Plant> plants) {
        return plants.stream().map(Plant::getAllWaterConsumptionInLiters).reduce(0.0, Double::sum);
    }

    @Named("daysTillHarvest")
    default int getDaysTillHarvest(List<Plant> plants) {
        return plants.stream().map(Plant::getDaysTillHarvest).reduce(0, Integer::sum);
    }
}

