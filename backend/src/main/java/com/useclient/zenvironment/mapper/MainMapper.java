package com.useclient.zenvironment.mapper;

import com.useclient.zenvironment.model.dao.Community;
import com.useclient.zenvironment.model.dao.Garden;
import com.useclient.zenvironment.model.dao.Plant;
import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.model.dao.challenge.Challenge;
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

    @Mapping(target = "challengeName", source = "challengeType.name")
    @Mapping(target = "challengeDescription", source = "challengeType.description")
    @Mapping(target = "previousTarget", expression = "java(challenge.getChallengeType().getLevelTargetFunction().apply(challenge.getLevel()-1))")
    @Mapping(target = "nextTarget", expression = "java(challenge.getChallengeType().getLevelTargetFunction().apply(challenge.getLevel()))")
    @Mapping(target = "currentProgress", expression = "java(challenge.getChallengeType().getProgressFunction().apply(challenge.getCommunity()))")
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
}

