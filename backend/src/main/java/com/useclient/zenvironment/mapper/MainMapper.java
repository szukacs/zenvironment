package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.*;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.model.dto.*;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {
                PlantTypeRepository.class
        }
)
public interface MainMapper {

    PlantTypeDto toDto(PlantType plantType);

    @Mapping(target = "plantId", source = "plant.id")
    @Mapping(target = "harvestUnit", source = "plant.plantType.harvestUnit")
    HarvestDto toDto(Harvest harvest);

    @Mapping(target = "gardenId", source = "garden.id")
    PlantDto toDto(Plant plant);

    List<PlantDto> toPlantDtoList(List<Plant> plants);

    MinimalCommunity toMinDto(Community community);

    CommunityDto toDto(Community community);

    @Mapping(target = "challengeName", source = "challengeType.name")
    @Mapping(target = "challengeDescription", source = "challengeType.description")
    @Mapping(target = "imageUrl", source = "challengeType.imageUrl")
    @Mapping(target = "color", source = "challengeType.color")
    ChallengeDto toDto(Challenge challenge);

    List<ChallengeDto> toChallengeDtoList(List<Challenge> challenges);

    @Mapping(target = "allProducedOxygenInKilograms", source = "plants", qualifiedByName = "summarizeOxygenProduction")
    @Mapping(target = "allFixatedCO2InKilograms", source = "plants", qualifiedByName = "summarizeCO2Fixation")
    @Mapping(target = "allWaterConsumptionInLiters", source = "plants", qualifiedByName = "summarizeWaterConsumption")
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uprootedAt", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    @Mapping(target = "plantType", source = "dto.plantTypeId", qualifiedByName = "getPlantTypeById")
    Plant toEntity(NewPlantDto dto, Garden garden);
}

