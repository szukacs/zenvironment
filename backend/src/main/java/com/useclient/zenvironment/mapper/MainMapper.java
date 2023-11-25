package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.*;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
import com.useclient.zenvironment.model.dto.*;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
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

    @Mapping(target = "exchangeId", source = "exchange.id")
    @Mapping(target = "vendorId", source = "exchange.garden.id")
    @Mapping(target = "receiverId", source = "exchange.receiverId")
    @Mapping(target = "description", source = "exchange.description")
    @Mapping(target = "profileImageUrl", source = "exchange.garden.imageUrl")
    @Mapping(target = "productImageUrl", source = "exchange.productImageUrl")
    @Mapping(target = "gardenName", source = "exchange.garden.name")
    @Mapping(target = "accepted", source = "exchange.accepted")
    ExchangeDto exchangeDto(Exchange exchange);

    @Mapping(target = "challengeName", source = "challengeType.name")
    @Mapping(target = "challengeDescription", source = "challengeType.description")
    @Mapping(target = "imageUrl", source = "challengeType.imageUrl")
    @Mapping(target = "color", source = "challengeType.color")
    ChallengeDto toDto(Challenge challenge);

    List<ChallengeDto> toChallengeDtoList(List<Challenge> challenges);

    @Mapping(target = "allProducedOxygenInKilograms", source = "plants", qualifiedByName = "summarizeOxygenProduction")
    @Mapping(target = "allFixatedCO2InKilograms", source = "plants", qualifiedByName = "summarizeCO2Fixation")
    @Mapping(target = "allWaterConsumptionInLiters", source = "plants", qualifiedByName = "summarizeWaterConsumption")
    @Mapping(target = "plantSummaries", source = "plants", qualifiedByName = "createPlantSummaries")
    GardenDto toDto(Garden garden);

    @Named("summarizeOxygenProduction")
    default double summarizeOxygenProduction(List<Plant> plants) {
        if (CollectionUtils.isEmpty(plants)) return 0.0;
        return plants.stream().map(Plant::getAllProducedOxygenInKilograms).reduce(0.0, Double::sum);
    }

    @Named("summarizeCO2Fixation")
    default double summarizeCO2Fixation(List<Plant> plants) {
        if (CollectionUtils.isEmpty(plants)) return 0.0;
        return plants.stream().map(Plant::getAllFixatedCO2InKilograms).reduce(0.0, Double::sum);
    }


    @Named("summarizeWaterConsumption")
    default double summarizeWaterConsumption(List<Plant> plants) {
        if (CollectionUtils.isEmpty(plants)) return 0.0;
        return plants.stream().map(Plant::getAllWaterConsumptionInLiters).reduce(0.0, Double::sum);
    }

    @Named("createPlantSummaries")
    default List<PlantSummary> createPlantSummaries(List<Plant> plants) {
        if (CollectionUtils.isEmpty(plants)) return List.of();
        var summaryMap = new HashMap<String, PlantSummary>();
        plants.forEach(plant -> {
            var plantType = plant.getPlantType();
            var oxygen = plant.getAllProducedOxygenInKilograms();
            var co2 = plant.getAllFixatedCO2InKilograms();
            summaryMap.compute(plantType.getName(), (plantName, summary) -> {
                if (summary == null) {
                    return new PlantSummary(toDto(plantType), 1, oxygen, co2);
                }
                summary.setAllProducedOxygenInKilograms(summary.getAllProducedOxygenInKilograms() + oxygen);
                summary.setAllFixatedCO2InKilograms(summary.getAllFixatedCO2InKilograms() + co2);
                summary.setPlantCount(summary.getPlantCount() + 1);
                return summary;
            });
        });
        return summaryMap.values().stream().toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    @Mapping(target = "plantType", source = "dto.plantTypeId", qualifiedByName = "getPlantTypeById")
    Plant toEntity(NewPlantDto dto, Garden garden);
}

